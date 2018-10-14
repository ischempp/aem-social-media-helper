package org.fhcrc.www.core.components;

import java.util.Map;

public interface SocialMediaHelper {
	
	/**
	 * @return the social media metadata for the current page
	 */
    Map<String, String> getMetadata();

}
