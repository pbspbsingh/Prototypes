package in.co.pbs.dao;

import in.co.pbs.entities.DomainAsin;
import in.co.pbs.entities.UserSessionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<DomainAsin> getasin() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<DomainAsin> getDomainUsedAsin(String domain, String searchQuery) {
		final Session session = sessionFactory.openSession();
		List<DomainAsin> list = new ArrayList<>();
		try {
			list = session.createQuery("from DomainAsin da where da.domain=:domain and da.searchQuery=:search order by da.count desc").setString("domain", domain)
			.setString("search", searchQuery).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return list;
	}

	public void saveOrUpdate(List<DomainAsin> searchResult) {
		final Session session = sessionFactory.openSession();
		try {
			final Transaction trx = session.beginTransaction();
			for (DomainAsin s : searchResult)
				session.saveOrUpdate(s);
			trx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateASIN(DomainAsin asinDetail) {
		final Session session = sessionFactory.openSession();
		try {
			final Transaction trx = session.beginTransaction();

			session.saveOrUpdate(asinDetail);

			trx.commit();
		} finally {
			session.close();
		}
	}

	public DomainAsin getASinDetail(String searchQuery, String domain, String asin) {
		DomainAsin asinDetail = null;
		final Session session = sessionFactory.openSession();
		try {
			final Criteria criteria = session.createCriteria(DomainAsin.class);
			criteria.add(Restrictions.eq("domain", domain));
			criteria.add(Restrictions.eq("searchQuery", searchQuery));
			criteria.add(Restrictions.eq("asin", asin));
			final List<?> list = criteria.list();
			if (list.size() == 1)
				asinDetail = (DomainAsin) list.get(0);
		} finally {
			session.close();
		}
		return asinDetail;
	}

	public UserSessionInfo getVote(String sessionId, String domain, String asin) {
		UserSessionInfo sessionVote = null;
		final Session session = sessionFactory.openSession();
		try {
			final Criteria criteria = session.createCriteria(UserSessionInfo.class);
			criteria.add(Restrictions.eq("sessionId", sessionId));
			criteria.add(Restrictions.eq("domain", domain));
			criteria.add(Restrictions.eq("asin", asin));
			final List<?> list = criteria.list();
			if (list.size() >= 1)
				sessionVote = (UserSessionInfo) list.get(0);
		} finally {
			session.close();
		}
		return sessionVote;
	}

	public void saveSessionVote(String id, String domain, String asin, boolean voteUp) {
		final Session session = sessionFactory.openSession();
		try {
			final Transaction trx = session.beginTransaction();

			final UserSessionInfo voteInfo = new UserSessionInfo(id, domain, asin, voteUp);
			session.save(voteInfo);

			trx.commit();
		} finally {
			session.close();
		}
	}

	public Map<String, Boolean> readSessionData(String id, String domain) {
		final Map<String, Boolean> sessionInfo = new HashMap<>();
		final Session session = sessionFactory.openSession();
		try {
			final List<?> list = session.createQuery("select usi.asin, usi.vote from UserSessionInfo usi where usi.sessionId=:sessionId and usi.domain=:domain")
			.setString("sessionId", id).setString("domain", domain).list();
			
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				Object[] data = (Object[]) it.next();
				sessionInfo.put((String) data[0], (Boolean) data[1]);
			}
			
		} finally {
			session.close();
		}
		return sessionInfo;
	}

	public Set<String> fetchAllASINs() {
		final Set<String> asins = new HashSet<>();
		final Session session = sessionFactory.openSession();
		try {
			final List<?> list = session.createQuery("select distinct da.asin from DomainAsin da").list();
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				asins.add((String) it.next());
			}
		} finally {
			session.close();
		}
		return asins;
	}
}
