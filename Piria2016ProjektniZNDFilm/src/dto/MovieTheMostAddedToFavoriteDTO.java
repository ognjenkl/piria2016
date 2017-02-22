package dto;

import java.io.Serializable;

public class MovieTheMostAddedToFavoriteDTO extends MovieDTO implements Serializable {


	private static final long serialVersionUID = 7235968866937238267L;

	private Integer addedToFavoriteSum;

	public Integer getAddedToFavoriteSum() {
		return addedToFavoriteSum;
	}

	public void setAddedToFavoriteSum(Integer addedToFavoriteSum) {
		this.addedToFavoriteSum = addedToFavoriteSum;
	}
	
	
}
