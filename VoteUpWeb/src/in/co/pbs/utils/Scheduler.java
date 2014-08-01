package in.co.pbs.utils;

import in.co.pbs.dao.CommonDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service()
public class Scheduler {

	private Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private CommonDao commonDAO;

	@Scheduled(fixedDelay = 60000)
	public void fetchAsinDescription() {
		try {
			AsinDescriptionFetcher worker = new AsinDescriptionFetcher(commonDAO, "/tmp/pbs/asin-info/");
			worker.startTask();
		} catch (Exception e) {
			logger.error("ASIN description task has failed.", e);
		}
	}

}
