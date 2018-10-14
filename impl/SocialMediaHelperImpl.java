/**
 * SocialMediaHelper based on code from the AEM Core Components:
 * https://github.com/Adobe-Marketing-Cloud/aem-core-wcm-components/blob/master/bundles/core/src/main/java/com/adobe/cq/wcm/core/components/internal/models/v1/SocialMediaHelperImpl.java
 * Author: Ian Schempp
 * Last Modified: 09.26.2018
 * Documentation available at https://wiki.fhcrc.org/display/AEMD/SocialMediaHelper
 */
package org.fhcrc.www.core.components.impl;

import org.fhcrc.www.core.components.SocialMediaHelper;
import org.fhcrc.www.core.Utils;
import org.fhcrc.www.core.Constants;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.sling.api.SlingHttpServletRequest;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import com.day.cq.wcm.api.Page;

import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(
		adaptables = {SlingHttpServletRequest.class},
		adapters = {SocialMediaHelper.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SocialMediaHelperImpl implements SocialMediaHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(SocialMediaHelperImpl.class);

    //Open Graph metadata property names
    static final String OG_TITLE = "og:title";
    static final String OG_URL = "og:url";
    static final String OG_TYPE = "og:type";
    static final String OG_SITE_NAME = "og:site_name";
    static final String OG_IMAGE = "og:image";
    static final String OG_DESCRIPTION = "og:description";
    static final String FB_APP_ID = "fb:app_id";
    static final String OG_PUBLISHER = "article:publisher";
    static final String OG_PUBLICATION_DATE = "article:published_time";
    static final String OG_PROFILE_FIRST_NAME = "profile:first_name";
    static final String OG_PROFILE_LAST_NAME = "profile:last_name";

    //Twitter metadata property names
    static final String TWITTER_CARD = "twitter:card";
    static final String TWITTER_SITE = "twitter:site";
    static final String TWITTER_TITLE = "twitter:title";
    static final String TWITTER_DESCRIPTION = "twitter:description";
    static final String TWITTER_IMAGE = "twitter:image";

    @ScriptVariable
    private Page currentPage = null;

    @Self
    private SlingHttpServletRequest request = null;

    @SlingObject
    private ResourceResolver resourceResolver;

    private Map<String, String> metadata;

    @Override
    public Map<String,String> getMetadata() {

        if (metadata == null) {

            initMetadata();

        }

        return metadata;

    }

    /**
     * Creates a LinkedHashMap suitable for interation using data-sly-list.
     * The key names are property attributes for meta tags while the values
     * are the corresponding content attributes.
     */
    private void initMetadata() {

        metadata = new LinkedHashMap<String, String>();
        WebsiteMetadata websiteMetadata = createMetadataProvider();
        metadata.put(OG_URL, websiteMetadata.getURL());
        metadata.put(OG_TITLE, websiteMetadata.getTitle());
        metadata.put(OG_DESCRIPTION, websiteMetadata.getDescription());
        metadata.put(OG_SITE_NAME, websiteMetadata.getSiteName());     
        metadata.put(OG_TYPE, websiteMetadata.getTypeName());
        metadata.put(FB_APP_ID, websiteMetadata.getAppId());
        metadata.put(TWITTER_TITLE, websiteMetadata.getTitle());
        metadata.put(TWITTER_SITE, websiteMetadata.getTwitterSite());
        metadata.put(TWITTER_DESCRIPTION, websiteMetadata.getDescription());
        
        if (websiteMetadata instanceof NewsMetadata) {
            NewsMetadata newsMetadata = (NewsMetadata) websiteMetadata;
            metadata.put(OG_IMAGE, newsMetadata.getImage());
            metadata.put(OG_PUBLISHER, newsMetadata.getArticlePublisher());
            metadata.put(OG_PUBLICATION_DATE, newsMetadata.getArticlePublishDate());
            metadata.put(TWITTER_CARD, newsMetadata.getTwitterCard());
            metadata.put(TWITTER_IMAGE, newsMetadata.getImage());
        } else if(websiteMetadata instanceof ProfileMetadata) {
            ProfileMetadata profileMetadata = (ProfileMetadata) websiteMetadata;
            metadata.put(OG_PROFILE_FIRST_NAME, profileMetadata.getFirstName());
            metadata.put(OG_PROFILE_LAST_NAME, profileMetadata.getLastName());
            metadata.put(TWITTER_CARD, profileMetadata.getTwitterCard());
            metadata.put(OG_IMAGE, profileMetadata.getImage());
            metadata.put(TWITTER_IMAGE, profileMetadata.getImage());
        } 

        if (!metadata.containsKey(TWITTER_CARD)) {
            metadata.put(TWITTER_CARD, websiteMetadata.getTwitterCard());
        }
        
    }

    /**
     * Instantiates the suitable metadata provider based on the contents of the current page.
     */
    private WebsiteMetadata createMetadataProvider() {

        // If there is a publication date, then it is a news article
        if (!currentPage.getProperties().get(Constants.PN_ARTICLE_PUBLICATION_DATE, "").isEmpty()) {
            LOGGER.debug("Creating NewsMetadata");
            return new NewsMetadataProvider();
        } else if (!currentPage.getProperties().get(Constants.PN_PROFILE_LAST_NAME, "").isEmpty()) {
            //If there is a last name, then it is a profile
            LOGGER.debug("Creating ProfileMetadata");
            return new ProfileMetaDataProvider();
        } else {
            LOGGER.debug("Creating default WebsiteMetadata");
            return new WebsiteMetadataProvider();
        }

    }

    /**
     * Provides metadata based on the content of a generic webpage.
     */
    private interface WebsiteMetadata {

        enum Type {website, article, profile}

        String getTitle();

        String getURL();

        Type getType();

        String getTypeName();

        //Implement getImage if every page has a social media image field
        //String getImage();

        String getDescription();

        String getSiteName();

        String getAppId();
        
        String getTwitterSite();

        String getTwitterCard();

    }

    private interface NewsMetadata extends WebsiteMetadata {

        String getImage();

        String getArticlePublisher();

        String getArticlePublishDate();

    }

    private interface ProfileMetadata extends WebsiteMetadata {

        String getFirstName();

        String getLastName();

        String getImage();

    }

    private class WebsiteMetadataProvider implements WebsiteMetadata {

        @Override
        public String getTitle() {

            try {

                String title = Utils.getTitle(currentPage, Utils.PAGE_TITLE_LEVEL);
                return title;

            } catch (IllegalArgumentException e) {

                LOGGER.error("Current Page is null");
                return "";

            }
            
        }

        @Override
        public String getURL() {

            try {

                String url = Utils.getCanonicalURL(resourceResolver, request, currentPage);
                return url;

            } catch (IllegalArgumentException e) {

                LOGGER.error("Could not create canonical URL");
                return "";
                
            }

        }

        @Override
        public Type getType() {

            return Type.website;

        }

        @Override
        public String getTypeName() {

            return getType().name();

        }

        @Override
        public String getDescription() {

            return currentPage.getDescription();

        }

        @Override
        public String getSiteName() {

            return Constants.ORGANIZATION_NAME;

        }

        @Override
        public String getAppId() {

            return Constants.FACEBOOK_APP_ID;

        }

        @Override
        public String getTwitterSite() {

            return "@" + Constants.TWITTER_USER_NAME;

        }

        @Override
        public String getTwitterCard() {

            return Constants.TWITTER_CARD_TYPE;

        }
        
    }

    /**
     * The use of super is not strictly necessary here, but wanted to have
     * all the functions visible so they could be easily changed if needed.
     */
    private class NewsMetadataProvider extends WebsiteMetadataProvider implements NewsMetadata {

        @Override
        public String getTitle() {

            return super.getTitle();

        }

        @Override
        public String getURL() {

            return super.getURL();

        }

        @Override
        public Type getType() {

            return Type.article;

        }

        @Override
        public String getTypeName() {

            return getType().name();

        }

        @Override
        public String getDescription() {

            return super.getDescription();

        }

        @Override
        public String getSiteName() {

            return super.getSiteName();

        }

        @Override
        public String getAppId() {

            return super.getAppId();

        }

        @Override
        public String getTwitterSite() {

            return super.getTwitterSite();

        }

        @Override
        public String getTwitterCard() {

            if (currentPage.getProperties().get(Constants.PN_ARTICLE_SOCIAL_MEDIA_IMAGE,"").isEmpty() &&
                currentPage.getProperties().get(Constants.PN_ARTICLE_IMAGE,"").isEmpty()) {

                return Constants.TWITTER_CARD_TYPE;

            } else {

                return Constants.TWITTER_CARD_TYPE_IMAGE;

            }
            
        }

        @Override
        public String getImage() {

            StringBuffer sb = new StringBuffer();
            String imageSrc = currentPage.getProperties().get(Constants.PN_ARTICLE_SOCIAL_MEDIA_IMAGE, "");

            //If there is no social media image, check for an article image
            if (imageSrc.isEmpty()) {

                imageSrc = currentPage.getProperties().get(Constants.PN_ARTICLE_IMAGE, "");

            }

            //If there is an image, construct an absolute URL for it
            if (!imageSrc.isEmpty()) {

                sb.append(Constants.SECURE_URL_PREFIX);
                sb.append(Constants.WWW_DOMAIN);
                sb.append(imageSrc);

                imageSrc = sb.toString();

            } 

            return imageSrc; 

        }

        @Override
        public String getArticlePublisher() {

            return Constants.FACEBOOK_URL;

        }

        @Override
        public String getArticlePublishDate() {

            Date pubDate = currentPage.getProperties().get(Constants.PN_ARTICLE_PUBLICATION_DATE, Date.class);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            String dateString = "";

            if (pubDate != null) {
                dateString = df.format(pubDate);
            }

            return dateString;

        }

    }

    /**
     * The use of super is not strictly necessary here, but wanted to have
     * all the functions visible so they could be easily changed if needed.
     */
    private class ProfileMetaDataProvider extends WebsiteMetadataProvider implements ProfileMetadata {

        @Override
        public String getTitle() {

            return super.getTitle();

        }

        @Override
        public String getURL() {

            return super.getURL();

        }

        @Override
        public Type getType() {

            return Type.profile;

        }

        @Override
        public String getTypeName() {

            return getType().name();

        }

        @Override
        public String getDescription() {

            return super.getDescription();

        }

        @Override
        public String getSiteName() {

            return super.getSiteName();

        }

        @Override
        public String getAppId() {

            return super.getAppId();

        }

        @Override
        public String getTwitterSite() {

            return super.getTwitterSite();

        }

        @Override
        public String getTwitterCard() {

            if (currentPage.getProperties().get(Constants.PN_PROFILE_PHOTO,"").isEmpty()) {

                return Constants.TWITTER_CARD_TYPE;

            } else {

                return Constants.TWITTER_CARD_TYPE_IMAGE;
                
            }
            
        }

        @Override
        public String getFirstName() {

            return currentPage.getProperties().get(Constants.PN_PROFILE_FIRST_NAME, "");

        }

        @Override
        public String getLastName() {

            return currentPage.getProperties().get(Constants.PN_PROFILE_LAST_NAME, "");
            
        }

        @Override
        public String getImage() {

            StringBuffer sb = new StringBuffer();
            String imageSrc = currentPage.getProperties().get(Constants.PN_PROFILE_PHOTO, "");

            if (!imageSrc.isEmpty()) {

                sb.append(Constants.SECURE_URL_PREFIX);
                sb.append(Constants.WWW_DOMAIN);
                sb.append(imageSrc);

                imageSrc = sb.toString();

            }
            
            return imageSrc;

        }

    }

}
