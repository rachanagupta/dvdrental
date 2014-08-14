package com.dvd.rental.capstone.business.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rachana
 * @since Aug 9, 2014
 * 
 */
public abstract class ComponentException extends Exception {

    private static final long serialVersionUID = -1593962536388181895L;

    protected final List<ComponentException.Detail> details;

    /**
     * Construct a new instance with no message or cause
     */
    public ComponentException() {
        this.details = new ArrayList<ComponentException.Detail>();
    }

    /**
     * Construct a new instance
     * 
     * @param details
     *            details of the exception
     */
    public ComponentException(List<ComponentException.Detail> details) {
        this.details = details;
    }

    /**
     * Construct a new instance. Exception will contain a single detail with the
     * provided message
     * 
     * @param message
     *            exception message
     * @param cause
     *            cause
     */
    public ComponentException(String message, Throwable cause) {
        super(message, cause);
        this.details = new ArrayList<ComponentException.Detail>();
        this.details.add(new ComponentException.Detail(0, message, null));
    }

    /**
     * Construct a new instance. Exception will contain a single detail with the
     * provided message and code
     * 
     * @param message
     *            exception message
     * @param code
     *            code to use for the detail
     */
    public ComponentException(String message, int code) {
        super(message, null);
        this.details = new ArrayList<ComponentException.Detail>();
        this.details.add(new ComponentException.Detail(code, message, null));
    }

    /**
     * Construct a new instance. Exception will contain a single detail with the
     * provided message and code as well as a cause.
     * 
     * @param message
     *            exception message
     * @param code
     *            code to use for the detail
     * @param cause
     *            cause of the exception
     */
    public ComponentException(String message, int code, Throwable cause) {
        super(message, cause);
        this.details = new ArrayList<ComponentException.Detail>();
        this.details.add(new ComponentException.Detail(code, message, null));
    }

    /**
     * Construct a new instance.Exception will contain a single detail with the
     * provided message
     * 
     * @param message
     *            exception message
     */
    public ComponentException(String message) {
        this(message, null);
    }

    /**
     * Returns the details for the exception
     * 
     * @return details for the exception
     */
    public List<ComponentException.Detail> getDetails() {
        return details;
    }

    /**
     * Returns the HTTP status code that should be used for mapping the
     * exception through HTTP.
     * 
     * @return the HTTP status code that should be used for mapping the
     *         exception through HTTP
     */
    public abstract int getHttpStatusCode();

    /**
     * Individual detail of an exception
     * 
     */
    public static class Detail {
        private final int code;
        private final String message;
        private final String path;

        /**
         * Construct a new instance
         * 
         * @param code
         *            component specific error code
         * @param message
         *            user displayed message
         * @param path
         *            optional path to an individual component of the request
         *            input data that is invalid
         */
        public Detail(int code, String message, String path) {
            this.code = code;
            this.message = message;
            this.path = path;
        }

        /**
         * Returns the error code
         * 
         * @return the error code
         */
        public int getCode() {
            return code;
        }

        /**
         * Returns the error message
         * 
         * @return the error message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Returns the path to the data the message is related to
         * 
         * @return path to the data the message is related to
         */
        public String getPath() {
            return path;
        }

    }

    /**
     * {@link ComponentException} thrown to indicate that the operation being
     * invoked is not currently supported
     */
    public static class UnsupportedOperation extends ComponentException {

        private static final long serialVersionUID = 5891540548218898890L;

        /**
         * Construct a new instance
         */
        public UnsupportedOperation() {
            super("Operation not supported");
        }

        /**
         * Construct a new instance
         * 
         * @param message
         *            the error message
         */
        public UnsupportedOperation(String message) {
            super(message);
        }

        @Override
        public final int getHttpStatusCode() {
            return 400;
        }

    }

    /**
     * {@link ComponentException} thrown to indicate that the request is
     * forbidden to access a resource it is invoking
     */
    public static class Forbidden extends ComponentException {

        private static final long serialVersionUID = 2033990673917727189L;

        /**
         * Construct a new instance
         * 
         * @param message
         *            the message to use
         */
        public Forbidden(String message) {
            super(message);
        }

        /**
         * Construct a new instance
         * 
         * @param message
         *            the message
         * @param code
         *            the error code
         */
        public Forbidden(String message, int code) {
            super(message, code);
        }

        @Override
        public final int getHttpStatusCode() {
            return 403;
        }
    }

    /**
     * {@link ComponentException} thrown to indicate that the request is not
     * currently authorized to access a resource it is invoking
     */
    public static class Unauthorized extends ComponentException {

        private static final long serialVersionUID = 7860910986225561166L;

        /**
         * Construct a new instance
         */
        public Unauthorized() {
            super();
        }

        /**
         * Construct a new instance
         * 
         * @param message
         *            the message
         */
        public Unauthorized(String message) {
            super(message);
        }

        @Override
        public final int getHttpStatusCode() {
            return 401;
        }
    }

    /**
     * {@link ComponentException} thrown to indicate that there is something
     * wrong with the configuration of the component
     */
    public static class ConfigurationException extends ComponentException {

        private static final long serialVersionUID = 8083265490831539655L;

        /**
         * Construct a new instance
         * 
         * @param message
         *            the message
         * @param cause
         *            cause of the configuration issue
         */
        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Construct a new instance
         * 
         * @param message
         *            the message
         */
        public ConfigurationException(String message) {
            super(message);
        }

        @Override
        public final int getHttpStatusCode() {
            return 500;
        }

    }

    /**
     * {@link ComponentException} thrown to indicate that the component request
     * was invalid
     */
    public static class InvalidRequest extends ComponentException {

        private static final long serialVersionUID = -568070304816310731L;

        /**
         * Construct a new instance
         * 
         * @param message
         *            message
         */
        public InvalidRequest(String message) {
            this(0, message);
        }

        /**
         * Construct a new instance
         * 
         * @param code
         *            error code
         * @param message
         *            message
         */
        public InvalidRequest(int code, String message) {
            super();
            details.add(new ComponentException.Detail(code, message, null));
        }

        /**
         * Construct a new instance
         * 
         * @param code
         *            error code
         * @param message
         *            message
         * @param path
         *            path to the field referred to in the message
         */
        public InvalidRequest(int code, String message, String path) {
            super();
            details.add(new ComponentException.Detail(code, message, path));
        }

        /**
         * Construct a new instance with the provided details
         * 
         * @param details
         *            the exception details
         */
        public InvalidRequest(List<ComponentException.Detail> details) {
            super(details);
        }

        @Override
        public final int getHttpStatusCode() {
            return 400;
        }

        /**
         * {@link ComponentException} thrown to indicate that some IO error
         * occurred
         */
        public static class IOException extends ComponentException {

            private static final long serialVersionUID = 8083265490831539655L;

            /**
             * Construct a new instance
             * 
             * @param message
             *            the message
             * @param cause
             *            cause of the configuration issue
             */
            public IOException(String message, Throwable cause) {
                super(message, cause);
            }

            /**
             * Construct a new instance
             * 
             * @param message
             *            the message
             */
            public IOException(String message) {
                super(message);
            }

            public IOException(Throwable failure) {
                super("There was a server communication problem", failure);
            }

            @Override
            public final int getHttpStatusCode() {
                return 500;
            }

        }
    }
    
        /**
         * {@link ComponentException} thrown to indicate that the component request
         * was not found 
         */
        public static class NotFound extends ComponentException {

            private static final long serialVersionUID = -568070304816310731L;

            /**
             * Construct a new instance
             * 
             * @param message
             *            message
             */
            public NotFound(String message) {
                this(0, message);
            }

            /**
             * Construct a new instance
             * 
             * @param code
             *            error code
             * @param message
             *            message
             */
            public NotFound(int code, String message) {
                super();
                details.add(new ComponentException.Detail(code, message, null));
            }

            /**
             * Construct a new instance
             * 
             * @param code
             *            error code
             * @param message
             *            message
             * @param path
             *            path to the field referred to in the message
             */
            public NotFound(int code, String message, String path) {
                super();
                details.add(new ComponentException.Detail(code, message, path));
            }

            /**
             * Construct a new instance with the provided details
             * 
             * @param details
             *            the exception details
             */
            public NotFound(List<ComponentException.Detail> details) {
                super(details);
            }

            @Override
            public final int getHttpStatusCode() {
                return 404;
            }
        }
    
    
}
