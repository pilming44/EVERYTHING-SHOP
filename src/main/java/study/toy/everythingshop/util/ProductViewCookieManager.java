package study.toy.everythingshop.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductViewCookieManager {
    private static final int COOKIE_MAX_AGE = 60 * 30; // 30분
    private static final String COOKIE_NAME = "productView";
    private static final String COOKIE_PATH = "/product/";

    public boolean isFirstProductView(Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = findCookie(request, COOKIE_NAME);

        if (oldCookie != null) {
            if (!isProductViewed(oldCookie, productNum)) {
                updateCookieValue(oldCookie, productNum);
                response.addCookie(oldCookie);
                return true;
            }
        } else {
            createNewProductViewCookie(response, productNum);
            return true;
        }

        return false;
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private boolean isProductViewed(Cookie cookie, Integer productNum) {
        return cookie.getValue().contains("[" + productNum + "]");
    }

    private void updateCookieValue(Cookie cookie, Integer productNum) {
        cookie.setValue(cookie.getValue() + "_[" + productNum + "]");
        cookie.setPath(COOKIE_PATH);
        cookie.setMaxAge(COOKIE_MAX_AGE);
    }

    private void createNewProductViewCookie(HttpServletResponse response, Integer productNum) {
        Cookie newCookie = new Cookie(COOKIE_NAME, "[" + productNum + "]");
        newCookie.setPath(COOKIE_PATH);
        newCookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(newCookie);
    }
}
