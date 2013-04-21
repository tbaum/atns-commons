package de.atns.common.web;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author tbaum
 * @since 21.10.2009
 */
public interface Action {

    void service() throws IOException, ServletException;
}
