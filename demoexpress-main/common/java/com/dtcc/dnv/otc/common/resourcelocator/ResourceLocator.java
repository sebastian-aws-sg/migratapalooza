package com.dtcc.dnv.otc.common.resourcelocator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dtcc.dnv.otc.common.resourcelocator.exception.LocatorException;

/**
 * @author Cognizant
 * @version 1.0 08/01/2007
 * 
 * Utility class to find resources (files, etc.), using a preconfigured
 * strategy.
 * 
 * @version	1.1					September 9, 2007			sv
 * Checked in for Cognizant.  Updated findResource() to return URL object variable
 * instead of null.
 * 
 * @version	1.2					September 17, 2007			sv
 * Checked in for Cogniazant.  Removed some redundant methods, and re-factored
 * logic.
 *
 * @version	1.3					September 18, 2007			sv
 * Checked in for Cognizant. Updated findResource() to return URL object variable instead 
 * of null.  Updated findResourceInClassPath() implementation.  Replaced replaceAll logic
 * with common StringUtils usage.
 * 
 * @version	1.4					October 18, 2007			sv
 * Udated logger to be static final.  Checked in for Cognizant.
 */
public class ResourceLocator implements Serializable, IResourceLocator {
    /**
     * logger instatiation
     */
    private static final Logger logger = Logger.getLogger(ResourceLocator.class);

    /**
     * list to hold the file system paths apart from current file system.
     */
    private List additionalFilesystemPaths;

    /**
     * instance of ResourceLocator
     */
    private static IResourceLocator resourceLocator = new ResourceLocator();

    /**
     * list to hold the web system paths apart from current file system.
     */
    private List additionalWebSystemPaths = null;

    /**
     * Private constructor
     *  
     */
    private ResourceLocator() {
        additionalFilesystemPaths = new ArrayList();
        additionalWebSystemPaths = new ArrayList();
    }

    /**
     * Instantiates a single instance of ResourceLocator
     * 
     * @return instance of ResourceLocator class
     */
    public static IResourceLocator getInstance() {
        return resourceLocator;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the URL
     * 
     * @return URL object matching the name, or <code>null</code> if
     *         <code>URL</code> can not be found is not readable
     * 
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    private URL findResource(String resourceName) throws LocatorException {
        resourceName = parseResourceName(resourceName);
        URL url = null;
        url = findResourceInClassPath(resourceName);
        if (url == null)
            url = findResourceInEarFile(resourceName);
        if (url == null)
            url = findResourceInFileSystem(resourceName);
        return url;
    }

    /**
     * Looks up a resource in the file system/EAR file/classpath.
     * 
     * @return InputStream object matching the name, or <code>null</code> if
     *         <code>InputStream</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    public URL findResource(String resourceName, int path) throws LocatorException {
        if (path == LocatorConstants.NO_PATH)
            return findResource(resourceName);
        resourceName = parseResourceName(resourceName);
        URL url = null;
        switch (path) {
        case LocatorConstants.CLASS_PATH:
            url = findResourceInClassPath(resourceName);
            break;
        case LocatorConstants.FILE_SYSTEM:
            url = findResourceInFileSystem(resourceName);
            break;
        case LocatorConstants.EAR_FILE:
            url = findResourceInEarFile(resourceName);
            break;
        default:
            throw new LocatorException(LocatorConstants.EC_URL_INVALID_PATH,
                    "The path/file specified for obtaining URL is invalid");
        }
        return url;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and return
     * the InputStream
     * 
     * @return InputStream object matching the name, or <code>null</code> if
     *         <code>InputStream</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    private InputStream findResourceAsStream(String resourceName) throws LocatorException {
        InputStream iStream = null;
        URL url = null;
        resourceName = parseResourceName(resourceName);
        url = findResourceInClassPath(resourceName);
        if (url == null)
            url = findResourceInEarFile(resourceName);
        if (url == null)
            url = findResourceInFileSystem(resourceName);
        try {
            iStream = url.openStream();
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_INPUTSTREAM_INVALID_PATH,
                    "Cannot open the input stream from URL");
        }
        return iStream;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the InputStream.
     * 
     * @return InputStream object matching the name, or <code>null</code> if
     *         <code>InputStream</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    public InputStream findResourceAsStream(String resourceName, int path)
            throws LocatorException {
        URL url = null;
        if (path == LocatorConstants.NO_PATH)
            return findResourceAsStream(resourceName);
        resourceName = parseResourceName(resourceName);
        InputStream iStream = null;
        switch (path) {
        case LocatorConstants.CLASS_PATH:
            url = findResourceInClassPath(resourceName);
            break;
        case LocatorConstants.FILE_SYSTEM:
            url = findResourceInFileSystem(resourceName);
            break;
        case LocatorConstants.EAR_FILE:
            url = findResourceInEarFile(resourceName);
            break;
        default:
            throw new LocatorException(LocatorConstants.EC_INPUTSTREAM_INVALID_PATH,
                    "The path/file specified for obtaining Input stream is invalid");
        }
        try {
            iStream = url.openStream();
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_INPUTSTREAM_INVALID_PATH,
                    "Cannot open the input stream from URL ");
        }
        return iStream;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the Properties Object
     * 
     * @return Properties object matching the name, or <code>null</code> if
     *         <code>Properties</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    private Properties findResourceAsProperties(String resourceName)
            throws LocatorException {
        InputStream iStream = null;
        URL url = null;
        Properties properties = null;
        resourceName = parseResourceName(resourceName);
        url = findResourceInClassPath(resourceName);
        if (url == null)
            url = findResourceInEarFile(resourceName);
        if (url == null)
            url = findResourceInFileSystem(resourceName);
        try {
            iStream = url.openStream();
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_INPUTSTREAM_INVALID_PATH,
                    "Cannot open the input stream from URL");
        }
        try {
            properties = convertStreamToProperties(iStream);
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_ERROR_PROPERTIES, e
                    .getMessage());
        }
        return properties;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the Properties Object
     * 
     * @return Properties object matching the name, or <code>null</code> if
     *         <code>Properties</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    public Properties findResourceAsProperties(String resourceName, int path)
            throws LocatorException {
        URL url = null;
        if (path == LocatorConstants.NO_PATH)
            return findResourceAsProperties(resourceName);
        InputStream iStream = null;
        Properties properties = null;
        resourceName = parseResourceName(resourceName);
        switch (path) {
        case LocatorConstants.CLASS_PATH:
            url = findResourceInClassPath(resourceName);
            break;
        case LocatorConstants.FILE_SYSTEM:
            url = findResourceInFileSystem(resourceName);
            break;
        case LocatorConstants.EAR_FILE:
            url = findResourceInEarFile(resourceName);
            break;
        default:
            throw new LocatorException(LocatorConstants.EC_PROPERTIES_INVALID_PATH,
                    "The path specified for obtaining properties is invalid");
        }
        try {
            iStream = url.openStream();
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_INPUTSTREAM_INVALID_PATH,
                    "Cannot open the input stream from input stream");
        }
        try {
            properties = convertStreamToProperties(iStream);
        } catch (IOException e) {
            throw new LocatorException(LocatorConstants.EC_ERROR_PROPERTIES, e
                    .getMessage());
        }
        return properties;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the File Object
     * 
     * @return File object matching the name, or <code>null</code> if
     *         <code>File</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    private File findResourceAsFile(String resourceName) throws LocatorException {
        String strPath = findResourceAsString(resourceName);
        File fileObj = null;
        try {
            if (strPath != null)
                fileObj = new File(strPath);

        } catch (SecurityException se) {
            throw new LocatorException(LocatorConstants.EC_SECURITY_EXCEPTION,
                    "Security exception " + se.getMessage());
        }
        return fileObj;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the File Object
     * 
     * @return File object matching the name, or <code>null</code> if
     *         <code>File</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    public File findResourceAsFile(String resourceName, int path) throws LocatorException {
        if (path == LocatorConstants.NO_PATH)
            return findResourceAsFile(resourceName);
        String strPath = findResourceAsString(resourceName, path);
        File fileObj = null;
        try {
            if (strPath != null)
                fileObj = new File(strPath);
        } catch (SecurityException e) {
            throw new LocatorException(LocatorConstants.EC_ERROR_CONVERTING_TO_FILE,
                    "Could not convert the resource as File object");
        }
        return fileObj;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the String Object
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    private String findResourceAsString(String resourceName) throws LocatorException {
        URL url = null;
        String strPath = null;
        resourceName = parseResourceName(resourceName);
        url = findResourceInClassPath(resourceName);
        if (url == null)
            url = findResourceInFileSystem(resourceName);
        if (url == null)
            url = findResourceInEarFile(resourceName);
        strPath = url.getPath();
        return strPath;
    }

    /**
     * Looks up a resource in the classpath, EAR file and file system and
     * returns the String Object
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable 
     * @throws LocatorException
     *             if <code>URL</code> is <code>null</code>
     */
    public String findResourceAsString(String resourceName, int path)
            throws LocatorException {
        if (path == LocatorConstants.NO_PATH)
            return findResourceAsString(resourceName);
        resourceName = parseResourceName(resourceName);
        URL url = null;
        String strPath = null;
        switch (path) {
        case LocatorConstants.CLASS_PATH:
            url = findResourceInClassPath(resourceName);
            break;
        case LocatorConstants.FILE_SYSTEM:
            url = findResourceInFileSystem(resourceName);
            break;
        case LocatorConstants.EAR_FILE:
            url = findResourceInEarFile(resourceName);
            break;
        default:
            throw new LocatorException(LocatorConstants.EC_STRING_INVALID_PATH,
                    "The path specified for obtaining file String is invalid");
        }
        if (url != null)
            strPath = url.getPath();
        return strPath;
    }

    /**
     * Looks up a file in all additional directory
     * 
     * @return file object matching the name, or <code>null</code> if
     *         <code>file</code> can not be found is not readable.
     */
    private File checkforAdditionalFileSystemPaths(String name) {
        if (!additionalFilesystemPaths.isEmpty()) {

            Iterator pi = this.additionalFilesystemPaths.iterator();
            while (pi.hasNext()) {
                try {
                    File file = new File((String) pi.next(), name);
                    if (file.exists()) {
                        return file;
                    }
                } catch (SecurityException se) {
                    logger.debug("Security Exp while accessing " + se);
                }

            }
        }
        return null;
    }

    /**
     * Looks up a file in the current directory
     * 
     * @return file object matching the name, or <code>null</code> if
     *         <code>file</code> can not be found is not readable.
     * @throws LocatorException
     */
    private File findFileInCurrentDirectory(String name) throws LocatorException {
        // look in the current directory
        String currentDirPath = System.getProperty("user.dir") + File.separator + name;
        try {
            File file = new File(currentDirPath);
            if (!file.exists() || !file.canRead()) {
                file = null;
            }
            return file;
        } catch (SecurityException se) {
            throw new LocatorException(LocatorConstants.EC_SECURITY_EXCEPTION,
                    "Security exception " + se.getMessage());
        }
    }

    /**
     * Adds the given String as a custom path for filesystem lookups. The path
     * can be relative or absolute and is <i>not </i> checked for existence.
     * 
     * @throws IllegalArgumentException
     *             if <code>path</code> is <code>null</code>.
     */
    public void addFileSystemPath(String path) {
        if (path != null) {
            this.additionalFilesystemPaths.add(path);
        } else {
            throw new IllegalArgumentException("Path must not be null.");
        }
    }

    /**
     * Converts the InputStream to properties
     * 
     * @throws IOException
     *             if <code>path</code> is <code>null</code>.
     */
    private Properties convertStreamToProperties(InputStream iStream) throws IOException {
        Properties props = new Properties();
        if (iStream != null) {
            props.load(iStream);
            return props;
        }
        return null;
    }

    /**
     * Looks up a resource in the classpath and returns the URL object
     * 
     * @return URL object matching the name, or <code>null</code> if
     *         <code>URL</code> can not be found is not readable.
     * @throws LocatorException
     */
    private URL findResourceInClassPath(String resourceName) throws LocatorException {
        URL url = null;
        if (!resourceName.startsWith("/"))
            resourceName = "/" + resourceName;
        url = (this.getClass()).getResource(resourceName);
        if (url != null)
            return url;
        String paths[] = split(System.getProperty("java.class.path"), File.pathSeparator);
        if (paths == null)
            return url;

        for (int index = 0; index < paths.length; index++) {
            try {
                File file = new File(paths[index], resourceName);
                if (file.exists() && !file.isDirectory() && file.canRead())
                    url = file.toURL();

            } catch (SecurityException se) {

            } catch (MalformedURLException e) {
                throw new LocatorException(LocatorConstants.EC_ERROR_MALFORMED_URL,
                        "Cannot convert file to URL " + e.getMessage());
            }
        }
        return url;
    }

    /**
     * Looks up a resource in the EAR file and return the URL Object
     * 
     * @return URL object matching the name, or <code>null</code> if
     *         <code>URL</code> can not be found is not readable.
     */
    private URL findResourceInEarFile(String resourceName) {
        URL url = null;
        // find the resource in all the directories including WEB-INF
        if (resourceName != null) {
            url = Thread.currentThread().getContextClassLoader()
                    .getResource(resourceName);
            if (url != null) {
                return url;
            } else {
                if (resourceName.indexOf("WEB-INF") < 0) {
                    if (resourceName.startsWith("/")) {
                        resourceName = resourceName.substring(1);
                    }
                    url = Thread.currentThread().getContextClassLoader().getResource(
                            "/WEB-INF/" + resourceName);
                } else
                    url = Thread.currentThread().getContextClassLoader().getResource(
                            resourceName);
            }
            /** Searches in additional web paths */
            if (url == null) {
                url = checkforAdditionalWebSystemPaths(resourceName);
            }
        }
        return url;
    }

    /**
     * Looks up a resource in the file system and returns the URL object
     * 
     * @return URL object matching the name, or <code>null</code> if
     *         <code>URL</code> can not be found is not readable.
     */
    private URL findResourceInFileSystem(String resourceName) throws LocatorException {
        URL url = null;
        File file = null;
        try {
            file = new File(resourceName);
            if (!file.exists() && !file.canRead())
                file = null;
        } catch (SecurityException se) {
            file = null;
        }
        if (file == null){
            try
            {
                file = findFileInCurrentDirectory(resourceName);
            } catch (LocatorException le)
            {
                 file = null;
            }
        }
        if (file == null)
            file = checkforAdditionalFileSystemPaths(resourceName);
        if (file != null) {
            try {
                url = file.toURL();
            } catch (MalformedURLException e) {
                throw new LocatorException(LocatorConstants.EC_ERROR_MALFORMED_URL,
                        "Cannot convert file to URL " + e.getMessage());
            }
        }
        return url;
    }

    /**
     * Parses the resource name and substitutes the JVM variable if found
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable.
     */
    private String parseResourceName(String resourceName) {
        String jvmProperty = null;
        while (resourceName.indexOf(LocatorConstants.EC_VARIABLE_PREFIX) >= 0) {
            jvmProperty = checkForENVVariable(resourceName);
            if (jvmProperty != null)
                resourceName = substituteENVValue(resourceName, jvmProperty);
        }
        return resourceName;
    }

    /**
     * Checks for ENV variables in the resource name and returns the JVM
     * variable value
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable.
     */
    private String checkForENVVariable(String resourceName) {
        String jvmProperty = null;
        int start = resourceName.indexOf(LocatorConstants.EC_VARIABLE_PREFIX);
        if (start < 0)
            return null;
        int end = resourceName.indexOf(LocatorConstants.EC_VARIABLE_SUFFIX, start);
        if (end < 0)
            return null;
        jvmProperty = resourceName.substring(start + 1, end); // To skip the "{"
        return jvmProperty;
    }

    /**
     * Substitutes JVM value in the resource name and returns the merged
     * resource name
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable.
     */
    private String substituteENVValue(String resourceName, String jvmProperty) {
        String jvmValue = null;
        jvmValue = System.getProperty(jvmProperty);
        if (jvmValue == null)
            return resourceName;
        if (!jvmValue.equals(""))
            resourceName = StringUtils.replace(resourceName, escape(jvmProperty),
                    jvmValue);
        return resourceName;
    }

    /**
     * Substitutes JVM value in the resource name and returns the merged
     * resource name
     * 
     * @return String object matching the name, or <code>null</code> if
     *         <code>String</code> can not be found is not readable.
     */
    private String escape(String source) {
        String target = "\\" + "$\\" + LocatorConstants.EC_VARIABLE_PREFIX + source
                + "\\" + LocatorConstants.EC_VARIABLE_SUFFIX;
        return target;
    }

    /**
     * Splits the string with the specified delimiter.
     * 
     * @param str
     *            string to be split
     * @param delim
     *            delimiter
     * @return string array containing the tokens
     */
    private String[] split(String str, String delim) {
        Vector v = new Vector();
        for (StringTokenizer tokenizer = new StringTokenizer(str, delim); tokenizer
                .hasMoreTokens(); v.addElement(tokenizer.nextToken()))
            ;
        String ret[] = new String[v.size()];
        v.copyInto(ret);
        return ret;
    }

    /**
     * Adds the given String as a custom path for web file system lookups. The
     * path can be relative or absolute and is <i>not </i> checked for
     * existence.
     * 
     * @throws IllegalArgumentException
     *             if <code>path</code> is <code>null</code>.
     */
    public void addWebSystemPath(String path) {
        if (path != null) {
            this.additionalWebSystemPaths.add(path);
        } else {
            throw new IllegalArgumentException("Path must not be null.");
        }
    }

    /**
     * Looks up a file in all additional web directory
     * 
     * @return file object matching the name, or <code>null</code> if
     *         <code>URL</code> can not be found is not readable.
     */
    private URL checkforAdditionalWebSystemPaths(String name) {
        URL url = null;
        if (!additionalWebSystemPaths.isEmpty()) {

            String suffix = null;
            //remove the "/" present in front of the resource name
            if (name.startsWith("/")) {
                suffix = name.substring(1);
            } else
                suffix = name;

            Iterator cpi = this.additionalWebSystemPaths.iterator();
            while (cpi.hasNext()) {
                String prefix = (String) cpi.next();

                /** Append the web path with "/" if it does not end with "/" */
                if (!prefix.endsWith("/")) {
                    prefix += "/";
                }
                String resourceName = prefix + suffix;

                //Check if the path is prefixed by WEB-INF
                if (!resourceName.startsWith("WEB-INF")) {
                    resourceName = "/WEB-INF/" + resourceName;
                }
                url = Thread.currentThread().getContextClassLoader().getResource(
                        resourceName);

            }
        }
        return url;
    }

    /**
     * remove the search file path
     * 
     * @return void
     */
    public void removeAllFileSystemPath() {
        this.additionalFilesystemPaths.clear();

    }

    /**
     * remove the web search file path
     * 
     * @return void
     */
    public void removeAllWebSystemPath() {
        this.additionalWebSystemPaths.clear();

    }

}
