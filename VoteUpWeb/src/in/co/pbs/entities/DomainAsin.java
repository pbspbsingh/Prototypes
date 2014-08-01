package in.co.pbs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "domain_search_asin")
public class DomainAsin {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(nullable = false)
	private int id;

	@Column(nullable = false)
	private String domain;

	@Column(nullable = false)
	private String searchQuery;

	@Column(nullable = false)
	private String asin;

	@Column(nullable = true)
	private Integer count;

	@Transient
	private String iconPath;
	@Transient
	private String title;
	@Transient
	private String price;
	@Transient
	private int displacement;
	@Transient
	private String reviewersCount;
	@Transient
	private String detailPageURL;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getRating() {
		return displacement;
	}

	public void setRating(double rating) {
		this.displacement = 206;
		int intValueOfRating = (int) rating;
		if (rating == intValueOfRating)
			displacement = (13 * 5) - 13 * intValueOfRating;
		else
			displacement = 194 - 13 * intValueOfRating;
	}

	public String getReviewersCount() {
		return reviewersCount;
	}

	public void setReviewersCount(String reviewersCount) {
		this.reviewersCount = reviewersCount;
	}

	public String getDetailPageURL() {
		return detailPageURL;
	}

	public void setDetailPageURL(String detailPageURL) {
		this.detailPageURL = detailPageURL;
	}

}
