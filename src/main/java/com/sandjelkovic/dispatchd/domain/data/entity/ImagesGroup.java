package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the ImagesGroup database table.
 */
@Entity
@Table(name = "ImagesGroup")
@NamedQuery(name = "ImagesGroup.findAll", query = "SELECT i FROM ImagesGroup i")
public class ImagesGroup extends BasicEntity implements Serializable {
	public static final int URL_LENGTH = 500;
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = URL_LENGTH)
	private String fanartFull;

	@Column(length = URL_LENGTH)
	private String fanartMedium;

	@Column(length = URL_LENGTH)
	private String fanartThumb;

	@Column(length = URL_LENGTH)
	private String fantartSmall;

	@Column(length = URL_LENGTH)
	private String posterFull;

	@Column(length = URL_LENGTH)
	private String posterMedium;

	@Column(length = URL_LENGTH)
	private String posterSmall;

	@Column(length = URL_LENGTH)
	private String posterThumb;

	public ImagesGroup() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFanartFull() {
		return this.fanartFull;
	}

	public void setFanartFull(String fanartFull) {
		this.fanartFull = fanartFull;
	}

	public String getFanartMedium() {
		return this.fanartMedium;
	}

	public void setFanartMedium(String fanartMedium) {
		this.fanartMedium = fanartMedium;
	}

	public String getFanartThumb() {
		return this.fanartThumb;
	}

	public void setFanartThumb(String fanartThumb) {
		this.fanartThumb = fanartThumb;
	}

	public String getFantartSmall() {
		return this.fantartSmall;
	}

	public void setFantartSmall(String fantartSmall) {
		this.fantartSmall = fantartSmall;
	}

	public String getPosterFull() {
		return this.posterFull;
	}

	public void setPosterFull(String posterFull) {
		this.posterFull = posterFull;
	}

	public String getPosterMedium() {
		return this.posterMedium;
	}

	public void setPosterMedium(String posterMedium) {
		this.posterMedium = posterMedium;
	}

	public String getPosterSmall() {
		return this.posterSmall;
	}

	public void setPosterSmall(String posterSmall) {
		this.posterSmall = posterSmall;
	}

	public String getPosterThumb() {
		return this.posterThumb;
	}

	public void setPosterThumb(String posterThumb) {
		this.posterThumb = posterThumb;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
