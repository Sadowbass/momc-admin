package com.momc.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest {

    private int page;
    private int limit;

    public int getOffset() {
        return getPage() * getLimit() - getLimit();
    }

    public static class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.getParameterType().isAssignableFrom(PageRequest.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

            int parsedPage = parsePage(request.getParameter("page"));
            int parsedLimit = parseLimit(request.getParameter("limit"));

            return new PageRequest(parsedPage, parsedLimit);
        }

        public int parsePage(String page) {
            int parse = parse(page, 1);
            return Math.max(parse, 1);
        }

        public int parseLimit(String limit) {
            int parse = parse(limit, PageObject.DEFAULT_CONTENTS_PER_PAGE);
            return parse < 10 ? PageObject.DEFAULT_CONTENTS_PER_PAGE : parse;
        }

        public int parse(String strToNum, int defaultValue) {
            try {
                return Integer.parseInt(strToNum);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
    }
}
