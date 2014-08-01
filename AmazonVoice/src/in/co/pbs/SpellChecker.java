package in.co.pbs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Servlet implementation class SpellChecker
 */
@WebServlet("/spellchecker")
public class SpellChecker extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String URL = "http://spellingcorrection-service-us.amazon.com:8080/speller/service?q=%s&mkt=1&category=%s&cid=client-search-dtam@amazon.com/979403689569f32d8a3c34bd20e01d0ceb857355";

	private static final Map<String, String> cache = new HashMap<>();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String searchKey = req.getParameter("searchKey");
		final String category = req.getParameter("category");
		if (searchKey != null && category != null) {
			if (cache.get(searchKey) == null)
				cache.put(searchKey, loadFromService(searchKey));

			res.setContentType("text/html");
			if (cache.get(searchKey) != null)
				res.getWriter().write(cache.get(searchKey));
			res.getWriter().close();
		} else
			throw new ServletException("Expected params (searchKey and category) not found");
	}

	private String loadFromService(String searchKey) throws IOException {
		searchKey = URLEncoder.encode(searchKey, "UTF-8");
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(String.format(URL, searchKey, "blended"));
		HttpResponse response = client.execute(request);

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		final StringBuilder builder = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			builder.append(line);
		}
		try {
			JSONArray array = new JSONArray(builder.toString());
			if (array.length() >= 2) {
				final JSONArray suggestion = array.getJSONArray(1);
				if (suggestion.length() >= 1) {
					final JSONArray output = suggestion.getJSONArray(0);
					if (output.length() >= 1)
						return output.getString(0);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
