/* (c) 2019 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.jsonld.configuration;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.geoserver.jsonld.builders.impl.RootBuilder;
import org.geoserver.platform.FileWatcher;
import org.geoserver.platform.resource.Resource;

/**
 * This class extends {@link FileWatcher} to provide functionalities to reload dynamically a json-ld
 * template
 */
public class JsonLdTemplateWatcher extends FileWatcher<RootBuilder> {

    public JsonLdTemplateWatcher(Resource resource) {
        super(resource);
    }

    public JsonLdTemplateWatcher(File file) {
        super(file);
    }

    /**
     * Parse Json-Ld template file and return a builder tree as a {@link RootBuilder}
     *
     * @param in
     * @return builderTree
     * @throws IOException
     */
    @Override
    public RootBuilder parseFileContents(InputStream in) throws IOException {
        ObjectMapper mapper =
                new ObjectMapper(new JsonFactory().enable(JsonParser.Feature.ALLOW_COMMENTS));
        JsonLdTemplateReader reader = new JsonLdTemplateReader(mapper.readTree(in));
        return reader.getRootBuilder();
    }

    public RootBuilder getJsonLdTemplate() throws IOException {
        return read();
    }
}
