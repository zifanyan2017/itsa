package filter;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.SysexMessage;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.StringUtils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Enumeration;
import java.util.Map;



public class PreFilter extends ZuulFilter {
    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestUrl = request.getRequestURL().toString();
        String sessionID = request.getHeader("sessionID");
        boolean isUser = false;
//start of comments
//
//        if(requestUrl.contains("user")){
//            System.out.println("calling user service");
//            isUser = true;
//        }
//        if(requestUrl.contains("session")){
//            System.out.println("calling user service");
//            isUser = true;
//        }
//        System.out.println(request.getHeader("sessionID"));
//
//        if(isNullOrEmpty(sessionID) && !isUser){
//            System.out.println("Authorisation is null or empty and it is not user service");
//            ctx.getResponse().setContentType("application/json");
//            ctx.setResponseBody("missing sessionID");
//            ctx.setResponseStatusCode(401);
//            ctx.setSendZuulResponse(false);
////            ctx.sendZuulResponse();
//            return null;
//        }else{
//            //URI uri = null;
//            try {
//
//                HttpClient httpClient = HttpClient.newBuilder()
//                        .build();
//
//                HttpRequest httpRequest = HttpRequest.newBuilder()
//                        .uri(URI.create("http://18.139.165.126:8084/exist/"+sessionID))
//                        .GET()   // this is the default
//                        .build();
//                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//
//                System.out.println("Response status code: " + response.statusCode());
//                System.out.println("Response headers: " + response.headers());
//                System.out.println("Response body: " + response.body());
//
//
//                JSONParser parser = new JSONParser();
//                JSONObject responseJson = (JSONObject) parser.parse(response.body());
//                System.out.println(responseJson.get("Exist"));
//
//                if(!(boolean)responseJson.get("Exist")){
//                    System.out.println("false");
//                    ctx.getResponse().setContentType("application/json");
//                    ctx.setResponseBody("sessionID is not valid");
//                    ctx.setResponseStatusCode(401);
//                    ctx.setSendZuulResponse(false);
//
//                }else{
//                    System.out.println("true");
//                    return null;
//                }
//
//            } catch (Exception  e) {
//                System.out.println(e);
//            }
//
//
//        }
        //end of comment
//        else if(authHeader.equals("yes")) {
//            System.out.println("Authorisation is yes");
//        }else{
//            System.out.println("Authorisation is not yes");
//        }



        System.out.println("Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());
        return null;
    }

}
