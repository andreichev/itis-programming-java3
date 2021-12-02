package ru.itis.sessionsexample.service;

import ru.itis.sessionsexample.service.model.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class SessionsManager {
    private final String cookieSessionKey = "JSESSIONID";
    private final Map<String, Session> cache = new HashMap<>();

    public Session getSession(boolean force, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> optionalKeyCookie = Arrays.stream(request.getCookies() == null ? new Cookie[]{} : request.getCookies())
                .filter(item -> item.getName().equals(cookieSessionKey))
                .findAny();

        Session session = null;
        if (optionalKeyCookie.isPresent()) {
            session = cache.get(optionalKeyCookie.get().getValue());
        }
        if (session != null) {
            return session;
        } else if(force) {
            session = new Session();
            String sessionId = UUID.randomUUID().toString();
            response.addCookie(new Cookie(cookieSessionKey, sessionId));
            cache.put(sessionId, session);
            return session;
        }
        return null;
    }
}
