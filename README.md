# aem-social-media-helper
Extension of the AEM Core Components Social Media Helper

## Purpose
This code is an extension of the AEM Core Components Social Media Helper. It will add all relevant meta tags to the head of an HTML document.

## How it works
The SocialMediaHelper looks at the structure of the Page it is called on to determine which set of metadata should be returned. In this case, there are three options:

1. If the Page in question has a property named _publicationDate_, then it is determined to be a News Article and the article metadata (an extension of the basic website metadata) is returned
2. If it instead has a property named _lastName_, then it is determined to be a Profile Page and the profile metadata (an extension of the basic website metadata) is returned
3. Otherwise, it is determined to be a website and the basic website metadata is returned.

The object that is returned is a LinkedHashMap, which can be iterated over using data-sly-list to write out each meta tag in turn.
