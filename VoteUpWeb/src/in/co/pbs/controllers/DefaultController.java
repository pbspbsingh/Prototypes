package in.co.pbs.controllers;

import in.co.pbs.dao.CommonDao;
import in.co.pbs.entities.DomainAsin;
import in.co.pbs.utils.RestClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

	@Autowired
	private CommonDao commonDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/index.html")
	public ModelAndView defaultPage(HttpSession session, @RequestParam(defaultValue = "books") String context, 
			@RequestParam(required = true) String domain, @RequestParam(defaultValue="300") int height) {
		context = context.trim().toLowerCase();
		domain = domain.trim().toLowerCase();
		
		List<DomainAsin> result = fetchAsinDetails(context, domain);
		
		Collections.sort(result, new Comparator<DomainAsin>() {
			@Override
			public int compare(DomainAsin o1, DomainAsin o2) {
				Integer i1 = o1.getCount() != null ? o1.getCount() : -999;
				Integer i2 = o2.getCount() != null ? o2.getCount() : -999;
				return -i1.compareTo(i2);
			}
		});
		final Map<String, Boolean> sessionData = commonDAO.readSessionData(session.getId(), domain);
		
		final ModelAndView mv = new ModelAndView("default");
		mv.addObject("asinDetails", result);
		mv.addObject("context", context);
		mv.addObject("domain", domain);
		mv.addObject("alreadyVote", sessionData);
		
		mv.addObject("title", "Community Wishlist");
		mv.addObject("height", height);
		return mv;
	}

	private List<DomainAsin> fetchAsinDetails(String searchQuery, String domain) {

		List<DomainAsin> searchResult = getSearchResult(searchQuery, domain);
		
		populateData(searchResult);
		
		if (searchResult.size() > 10)
			searchResult = searchResult.subList(0, 10);

		commonDAO.saveOrUpdate(searchResult);

		return searchResult;
	}

	private void populateData(List<DomainAsin> searchResult) {
		final Map<String, JSONObject> asinInfos = new HashMap<String, JSONObject>();
		final RestClient client = new RestClient("http://ws.assoc-amazon.com/widgets/q");
		client.AddParam("TemplateId", "PubStudio");
		client.AddParam("ServiceVersion", "20070822");
		client.AddParam("MarketPlace", "US");
		client.AddParam("Operation", "GetDetails");
		client.AddParam("InstanceId", "1367560449185");
		client.AddParam("ItemId", createAsinString(searchResult));
		client.AddParam("Callback", "dummyCallBack");
		try {
			client.execute(in.co.pbs.utils.RequestMethod.GET);
			if (client.getResponseCode() == 200) {
				String response = client.getResponse();
				if (response.length() < 16)
					return;
				response = response.substring(15, response.length() - 1);
				final JSONObject jsonObject = new JSONObject(response);
				final JSONArray resultArray = jsonObject.getJSONArray("results");
				for (int i = 0; i < resultArray.length(); i++) {
					final JSONObject jsonObj = resultArray.getJSONObject(i);
					asinInfos.put(jsonObj.getString("ASIN"), jsonObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Iterator<DomainAsin> it=searchResult.iterator();it.hasNext();) {
			final DomainAsin da = it.next();
			final JSONObject result = asinInfos.get(da.getAsin());
			if (result == null) {
				it.remove();
				continue;
			}
			String iconPath;
			try {
				iconPath = result.getString("LargeImageUrl");
				if(!iconPath.equals(""))
					iconPath = iconPath.substring(0, iconPath.lastIndexOf(".")) + "._SL75_" + iconPath.substring(iconPath.lastIndexOf("."));
				da.setIconPath(iconPath);
				da.setTitle(result.getString("Title"));
				da.setPrice(result.getString("Price"));
				da.setReviewersCount(result.getString("TotalReviews"));
				da.setDetailPageURL(result.getString("DetailPageURL"));
				da.setRating(Double.parseDouble(result.getString("Rating")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private final StringBuilder builder = new StringBuilder();
	private String createAsinString(List<DomainAsin> searchResult) {
		builder.setLength(0);
		for(DomainAsin s : searchResult)
			builder.append(s.getAsin()).append(",");
		
		return builder.length() > 2 ? builder.substring(0, builder.length() - 1) : null;
	}

	private List<DomainAsin> getSearchResult(String searchQuery, String domain) {
		final List<DomainAsin> asinFromDb = commonDAO.getDomainUsedAsin(domain, searchQuery);
		if (asinFromDb.size() < 10) {
			try {
				RestClient queryAsin = new RestClient("https://widgets.amazon.com/xml?ItemPage=1&MarketPlace=US&SearchIndex=All&Keywords="
						+ URLEncoder.encode(searchQuery, "UTF-8") + "&Service=itemsearch");
				queryAsin.execute(in.co.pbs.utils.RequestMethod.GET);
				if (queryAsin.getResponseCode() == 200) {
					String response = queryAsin.getResponse();
					JSONArray array = new JSONArray(response);
					for (int i = 0; i < array.length(); i++) {
						JSONObject asinObj = array.getJSONObject(i);
						final DomainAsin domainASin = new DomainAsin();
						domainASin.setAsin(asinObj.getString("ASIN"));
						domainASin.setDomain(domain);
						domainASin.setSearchQuery(searchQuery);
						asinFromDb.add(domainASin);
					}
				}
			} catch (UnsupportedEncodingException | JSONException e) {
				e.printStackTrace();
			}
		}
		return asinFromDb;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/popover.html")
	public ModelAndView getPopoup(){
		return new ModelAndView("popup");
	}
}
