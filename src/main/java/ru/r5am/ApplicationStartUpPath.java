package main.java.ru.r5am;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by alien on 25.06.2015.
 */

class ApplicationStartUpPath {

    /**
     * @return Путь к каталогу, в котором расположен jar-файл с классом
     *         ApplicationStartUpPath.
     */
    Path getApplicationStartUp() throws UnsupportedEncodingException,
            MalformedURLException {
        URL startupUrl = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        Path path = null;
        try {
            path = Paths.get(startupUrl.toURI());
        } catch (Exception e) {
            try {
                path = Paths.get(new URL(startupUrl.getPath()).getPath());
            } catch (Exception ipe) {
                path = Paths.get(startupUrl.getPath());
            }
        }
        path = path.getParent();
        return path;
    }
}
