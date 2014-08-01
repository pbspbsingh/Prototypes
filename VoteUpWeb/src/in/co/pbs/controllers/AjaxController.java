package in.co.pbs.controllers;

import in.co.pbs.dao.CommonDao;
import in.co.pbs.entities.DomainAsin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/ajax/**")
public class AjaxController {
	
	@Autowired
	private CommonDao commonDAO;
	
	@RequestMapping(value="init.html")
	public void initializeASIN(HttpSession session, HttpServletResponse response, @RequestParam(required=true, value="asin") String asin,
			@RequestParam(value="callback", defaultValue="__callBack") String callback, @RequestParam(required=true) String context, 
			@RequestParam(required=true) String domain) throws IOException, JSONException {
		context = context.trim().toLowerCase();
		domain = domain.trim().toLowerCase();
		
		final JSONObject jsonObj = new JSONObject();
		final DomainAsin asinDetail = commonDAO.getASinDetail(context, domain, asin);
		if (asinDetail == null) {
			jsonObj.put("isError", true);
		} else {
			if (asinDetail.getCount() == null)
				asinDetail.setCount(0);
			else
				asinDetail.setCount(asinDetail.getCount() + 1);

			commonDAO.updateASIN(asinDetail);
			jsonObj.put("voteCount", String.valueOf(asinDetail.getCount()));
		}

		response.getWriter().write(callback + "('" + jsonObj.toString() + "')");
		response.setContentType("application/json");
	};
	
	@RequestMapping(value="vote.html")
	public void voteASIN(HttpSession session, HttpServletResponse response, @RequestParam(required=true, value="asin") String asin,
			@RequestParam(value="callback", defaultValue="__callBack") String callback, @RequestParam(required=true) String context, 
			@RequestParam(required=true) String domain, @RequestParam(required=true) boolean voteUp) throws IOException, JSONException {
		
		context = context.trim().toLowerCase();
		domain = domain.trim().toLowerCase();
		asin = asin.trim();

		final JSONObject jsonObj = new JSONObject();
		
		if (commonDAO.getVote(session.getId(), domain, asin) != null) {
			jsonObj.put("isError", true);
		} else {
			final DomainAsin asinDetail = commonDAO.getASinDetail(context, domain, asin);
			if (asinDetail == null) {
				jsonObj.put("isError", true);
			} else {
				int voteCount = asinDetail.getCount();
				if (voteUp)
					voteCount++;
				else
					voteCount--;
				
				asinDetail.setCount(voteCount);

				commonDAO.updateASIN(asinDetail);
				commonDAO.saveSessionVote(session.getId(), domain, asin, voteUp);
				jsonObj.put("voteCount", String.valueOf(asinDetail.getCount()));
			}
		}

		response.getWriter().write(callback + "('" + jsonObj.toString() + "')");
		response.setContentType("application/json");
	}

	
	@RequestMapping(value="asinDesc.html")
	public void asinDesc(HttpServletResponse response, @RequestParam(required=true, value="asin") String asin, 
			@RequestParam(value="callback", defaultValue="__callBack")String callback) throws IOException, JSONException {
		asin = asin.trim();
		
		final StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/tmp/pbs/asin-info/" + asin + ".pbs")))) {
			String line = null;
			while ((line = reader.readLine()) != null)
				builder.append(line.trim());
		}
		final JSONObject jsonObj = new JSONObject();
		final String result = URLEncoder.encode(builder.toString(), "UTF-8");
		
		if (result.trim().equals(""))
			jsonObj.put("descNotAvailable", true);
		else
			jsonObj.put("asinDesc", result.trim());
		
		response.getWriter().write(callback + "('" + jsonObj.toString() + "')");
		response.setContentType("application/json");
	}
}
