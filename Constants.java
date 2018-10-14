package org.fhcrc.www.core;

public class Constants {
	
	public static final String ORGANIZATION_NAME = "Fred Hutch";
    
    
    public static final String WWW_DOMAIN = "www.fredhutch.org";
    public static final String WWW_LOCALE = "en_US";
    public static final String WWW_LANG = "en";
	public static final String HTML_EXTENSION = ".html";
    public static final String URL_ENCODING = "UTF-8";
    
    /*~~~~~~~~~~~~~~~~~~~ AEM ~~~~~~~~~~~~~~~~~~~~*/

    public static final String INTERNAL_LINK_PREFIX = "/content";
    public static final String AEM_SUBTITLE = "subtitle";
    public static final String AEM_PAGE = "cq:Page";
    public static final String PN_PAGE_TAGS = "jcr:content/cq:tags";
    public static final String PN_PAGE_TEMPLATE = "jcr:content/cq:template";

    /*~~~~~~~~~~~~~~~~~ Profiles ~~~~~~~~~~~~~~~~~*/

    public static final String PN_PROFILE_PHONE = "phoneNumber";
    public static final String PN_PROFILE_FAX = "faxNumber";
    public static final String PN_PROFILE_EMAIL = "email";
    public static final String PN_PROFILE_MAILSTOP = "mailStop";
    public static final String PN_PROFILE_FIRST_NAME = "firstName";
    public static final String PN_PROFILE_LAST_NAME = "lastName";
    public static final String PN_PROFILE_DEGREES = "degrees";
    public static final String PN_PROFILE_TITLE = "nameTitle";
    public static final String PN_PROFILE_PHOTO = "photo";
    public static final String PN_PROFILE_WEBSITE = "websiteURL";
    public static final String PN_PROFILE_CV = "cv";
    public static final String PN_PROFILE_APPOINTMENTS = "./appointments";
    public static final String PN_PROFILE_APPOINTMENT_TITLE = "title";
    public static final String PN_PROFILE_APPOINTMENT_ORG = "organization";

    /*~~~~~~~~~~~~~~ News Articles ~~~~~~~~~~~~~~~*/

    public static final String PN_ARTICLE_AUTHOR_FIRST_NAME = "author/firstName";
    public static final String PN_ARTICLE_AUTHOR_LAST_NAME = "author/lastName";
    public static final String PN_ARTICLE_ORGANIZATION = "author/organization";
    public static final String PN_ARTICLE_PUBLICATION_DATE = "publicationDate";
    public static final String PN_ARTICLE_IMAGE = "articleImage";
    public static final String PN_ARTICLE_SOCIAL_MEDIA_IMAGE = "socialMediaImage";

    /*~~~~~~~~~~~~~~~ Social Media ~~~~~~~~~~~~~~~*/
    
    /* Facebook */
    public final static String FACEBOOK_APP_ID = "258306851682396";
    public final static String FACEBOOK_URL = "https://www.facebook.com/HutchinsonCenter";

	/* Twitter */
	public final static String TWITTER_BASE = "twitter.com/intent/tweet";
	public final static String TWITTER_USER_NAME = "fredhutch";
	public final static String PN_TWITTER_TWEET_TEXT = "text";
    public final static String PN_TWITTER_URL = "url";
    public final static String PN_TWITTER_HASHTAGS = "hashtags";
    public final static String PN_TWITTER_USERNAME = "via";
    public final static String PN_TWITTER_RELATED_USERS = "related";
    public final static String TWITTER_CARD_TYPE = "summary";
    public final static String TWITTER_CARD_TYPE_IMAGE = "summary_large_image";
	
	/* LinkedIn */
	public final static String LINKEDIN_BASE = "www.linkedin.com/shareArticle";
    public final static String PN_LINKEDIN_URL = "url";
    public final static String PN_LINKEDIN_TITLE = "title";
    public final static String PN_LINKEDIN_SUMMARY = "summary";
    public final static String PN_LINKEDIN_SOURCE = "source";
    /* 
     * LinkedIn requires this argument with this value
     * https://developer.linkedin.com/docs/share-on-linkedin 
     */
    public final static String PN_LINKEDIN_MINI = "mini=true";

    /* Email */
    public static final String MAILTO_URL_PREFIX = "mailto:";
    public static final String MAILTO_SUBJECT = "subject";
    public static final String MAILTO_BODY = "body";

    /*~~~~~~~~~~ Universal items ~~~~~~~~~~*/
    public static final String SECURE_URL_PREFIX = "https://";
    public static final String TELEPHONE_URL_PREFIX = "tel:";
	public final static String URL_PARAMETER_PREFIX = "?";
    public final static String URL_PARAMETER_DELIMITER = "&";
    public final static String URL_PARAMETER_EQUALS = "=";
    public final static String NEWLINE = "\n";

}
