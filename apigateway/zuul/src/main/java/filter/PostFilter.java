package filter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
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
        System.out.println("Inside Post Filter");
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
//        InputStream servletResponse = context.getResponseDataStream();
        String requestUrl = request.getRequestURL().toString();
        System.out.println(requestUrl);

//        try {
//            InputStream in = context.getResponseDataStream();
//            StringBuffer result = new StringBuffer();
//            byte[] b = new byte[1024];
//            for (int n; (n = in.read(b)) != -1; ) {
//                result.append(new String(b, 0, n));
//            }
//            System.out.println(result.toString());
////            requestContext.setSendZuulResponse(true);
////            requestContext.setResponseStatusCode(200);
////            requestContext.setResponseBody(result.toString());
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        return null;
    }
}
