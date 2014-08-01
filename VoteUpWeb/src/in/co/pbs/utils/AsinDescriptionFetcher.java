package in.co.pbs.utils;

import in.co.pbs.dao.CommonDao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AsinDescriptionFetcher {

	private final File folder;
	private final CommonDao dao;

	private Log logger = LogFactory.getLog(getClass());

	public AsinDescriptionFetcher(CommonDao dao, String folderLocation) {
		this.dao = dao;
		this.folder = new File(folderLocation);
		if (!folder.exists()) {
			if (!folder.mkdirs())
				throw new IllegalStateException("Failed to create folder: " + folderLocation);
		}
	}

	public void startTask() {
		final Set<String> newAsins = getNewASINs();
		for (String asin : newAsins) {
			long startTime = System.currentTimeMillis();
			final File asinDescFile = new File(folder, asin + ".pbs");
			try {
				RestClient client = new RestClient("http://www.amazon.com/aw/dp/" + asin);
				client.execute(RequestMethod.GET);
				if (client.getResponseCode() == 200) {
					String response = client.getResponse();
					int descriptionStart = response.indexOf("id=\"ps-content\"");
					if (descriptionStart == -1) {
						logger.info("Description not found in the response for asin: " + asin);
						try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(asinDescFile)))) {
							writer.write("");
						}
						continue;
					}

					descriptionStart = response.indexOf("<noscript>", descriptionStart);
					if (descriptionStart != -1) {
						int descriptionEnd = response.indexOf("</noscript>", descriptionStart);
						if (descriptionEnd != -1) {
							response = response.substring(descriptionStart + 10, descriptionEnd);

							try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(asinDescFile)))) {
								writer.write(response);
							}
						}
					}

				} else {
					logger.warn("RestClient returned response code: " + client.getResponseCode() + " for asin: " + asin);
				}
			} catch (Exception e) {
				logger.error("Error while fetching description for: " + asin);
			}
			logger.info("Time take to fetch Detail of ASIN " + asin + " is: " + (System.currentTimeMillis() - startTime));
		}
	}

	private Set<String> getNewASINs() {
		final Set<String> allAsinInSystem = dao.fetchAllASINs();
		final Set<String> existingASINs = getExistingsFiles();

		for (String asin : existingASINs) {
			allAsinInSystem.remove(asin);
		}
		return allAsinInSystem;
	}

	private Set<String> getExistingsFiles() {
		final File[] fileList = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.toLowerCase().endsWith(".pbs"))
					return true;
				return false;
			}
		});
		final Set<String> set = new HashSet<>();
		for (File file : fileList) {
			String name = file.getName();
			name = name.substring(0, name.lastIndexOf("."));
			set.add(name);
		}
		return set;
	}

}
