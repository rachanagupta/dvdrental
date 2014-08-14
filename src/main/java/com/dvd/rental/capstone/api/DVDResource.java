package com.dvd.rental.capstone.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dvd.rental.capstone.business.entity.Borrow;
import com.dvd.rental.capstone.business.entity.DVD;
import com.dvd.rental.capstone.business.exception.ComponentException;
import com.dvd.rental.capstone.business.exception.ComponentException.InvalidRequest;
import com.dvd.rental.capstone.business.service.DVDManager;
import com.dvd.rental.capstone.external.BorrowTo;
import com.dvd.rental.capstone.external.DVDTo;
import com.dvd.rental.capstone.external.SearchTo;
import com.dvd.rental.capstone.external.Utils;

/**
 * DVDResource
 * <p>
 * Handle the API related to the DVD
 * </p>
 * <p>
 * Security check 
 * <ul> 
 *   <li> Using UUID instead of plain int or long ids to prevent the hackers from guessing the ids and access the information. </li>
 *   <li> Not accepting the user id from the json - this prevents the hackers from manipulating the user id. Accept it form the header 
 *   </li>
 * </ul>
 *   
 * @author rachana
 * @since Aug, 8 2014
 * 
 */
@Controller
@Path("dvd")
public class DVDResource {
    /**
     * Manager class to deal with business logic
     */
    @Autowired
    private DVDManager manager;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Share the dvds 
     * Only lender can share DVD
     * Example 
     * To share the dvds 
        Url : localhost:8080/dvd-api/rest/dvd/share  
        Request : [
            "3f533253-b5ac-48e5-b816-a19852bc9b07"    
        ]
        Header : Content-Type: application/json
                 X_TRIPADIVSOR_USER_ID: db064348-b727-4f16-942f-13a7220c1252
     *  otherwise respective {@link Status} with message will be returned
     *  [
            {
                "code":0,
                "message":" lender id is required", 
                "path":null
            }
        ]
     *   
     * @param dvs ids 
     * @param request {@link HttpServletRequest}
     * @return {@link Response}
     * @throws ComponentException
     */
    @POST
    @Path("share")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response share(List<String> dvds, @Context HttpServletRequest request)
            throws ComponentException {
        // fetch the user id from the request header, instead of asking it.
        // This
        // will prevent
        // to change the user id by hackers
        String userId = (String) request.getHeader("X_TRIPADIVSOR_USER_ID");
        if (StringUtils.isBlank(userId)) {
            throw new InvalidRequest(" Lender id is required");
        }
        if (CollectionUtils.isEmpty(dvds)) {
            throw new InvalidRequest(" Atleast 1 DVD is required");
        }
        List<UUID> uuids = new ArrayList<UUID>();
        for (String stringId : dvds) {
            uuids.add(UUID.fromString(stringId));
        }
        manager.share(uuids, UUID.fromString(userId));
        // if everything goes good send 200 response back
        return Response.status(Status.OK).build();
    }
    /**
     * To search the particular DVD 
     * Note : instead of making search at GET I am doing post to add more attributes 
     * in future.
     * Example : 
     * Request : {
          "price":9.99, 
          "name":"Start wars"
       }
       Response : [
            {
                "id": "3f533253-b5ac-48e5-b816-a19852bc9b07",
                "movieId": "1908bca1-0369-486c-9aab-080fdf677c1d",
                "price": 9.99,
                "userId": "db064348-b727-4f16-942f-13a7220c1252",
                "descripton": "Star Wars ",
                "addedOn": 1407654000000,
                "status": "FRIED"
            }
        ]
     * @param searchTo
     * @return
     * @throws ComponentException
     */
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response find(SearchTo searchTo) throws ComponentException {
        @SuppressWarnings("unchecked")
        Map<String, Object> searchCriteria = objectMapper.convertValue(
                searchTo, Map.class);
        List<DVD> dvds = manager.search(searchCriteria);
        List<DVDTo> tos = Utils.transform(dvds, DVDTo.class);
        //if no DVD is found send back HTTP CODE 404
        if(CollectionUtils.isEmpty(tos)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.status(Status.OK).entity(tos).build();
    }

    /**
     * To find the DVD by id
     * Url : localhost:8080/dvd-api/rest/dvd/3cefe7a9-db6e-46fa-ae34-6367dd50f488
     * HTTP METHOD : GET
        {
            "id": "3f533253-b5ac-48e5-b816-a19852bc9b07",
            "movieId": "1908bca1-0369-486c-9aab-080fdf677c1d",
            "price": 9.99,
            "userId": "db064348-b727-4f16-942f-13a7220c1252",
            "descripton": "Star Wars ",
            "addedOn": 1407567600000,
            "status": "FRIED"
        }
     *
     * @param id {@link UUID}
     * @return {@link Response}
     * @throws ComponentException
     */
    @GET
    @Path("{uuid}")
    @Produces(APPLICATION_JSON)
    public Response find(@PathParam("uuid") UUID id) throws ComponentException {
        if (id == null) {
            throw new InvalidRequest(" DVD id is required");
        }
        DVD dvd = manager.findDVDById(id);
        DVDTo dvdTo = new DVDTo();
        dvdTo = Utils.transform(dvd, dvdTo);
        //if no DVD is found send back HTTP CODE 404
        if(dvdTo ==null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.status(Status.OK).entity(dvdTo).build();
    }
    /**
     * To borrow the DVD
     * Url : localhost:8080/dvd-api/rest/dvd/borrow
     * HTTP METHOD : PUT
        Request : {
          "dvdId":"3f533253-b5ac-48e5-b816-a19852bc9b07",
          "dueDate":"2014-09-12"
        }
        Header : Content-Type: application/json
                 X_TRIPADIVSOR_USER_ID: db064348-b727-4f16-942f-13a7220c1252
                 
     * @param borrowTo
     * @param request
     * @return
     * @throws ComponentException
     */
    @PUT
    @Path("borrow")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response borrow(BorrowTo borrowTo,
            @Context HttpServletRequest request) throws ComponentException {
        if (borrowTo == null) {
            throw new InvalidRequest("Please provide the information to borrow the DVD");
        }
        if(StringUtils.isBlank(borrowTo.getDvdTitle())) {
            throw new InvalidRequest("Please provide DVD title you want to borrow ");
        }
        if(borrowTo.getBorrowedOn()!=null) {
            throw new InvalidRequest(" Borrower cannot specify the borrowing date ");
        }
        // fetch the user id from the request header, instead of asking it. This
        // will prevent
        // to change the user id by hackers
        String userId = (String) request.getHeader("X_TRIPADIVSOR_USER_ID");
        if (StringUtils.isBlank(userId)) {
            throw new InvalidRequest(" Borrower Id is required");
        }
        Borrow borrow = new Borrow();
        borrow = Utils.transform(borrowTo, borrow);
        borrow.setUserId(UUID.fromString(userId));
        manager.borrow(borrow);
        return Response.status(Status.OK).build();
    }
}
