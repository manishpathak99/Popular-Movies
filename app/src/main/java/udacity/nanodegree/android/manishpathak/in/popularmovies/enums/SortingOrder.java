package udacity.nanodegree.android.manishpathak.in.popularmovies.enums;

import udacity.nanodegree.android.manishpathak.in.popularmovies.constants.AppConstants;

/**
 * Created by manishpathak on 3/9/16.
 */
public enum SortingOrder {

    MOST_POPULAR(AppConstants.SORT_BY_POPULARITY), HIGHEST_RATED(AppConstants.SORT_BY_RATING);

    private String sortedBy;

    SortingOrder (String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public String getSortedBy(){
        return this.sortedBy;
    }

    public static SortingOrder getEnum(String value) {
        for(SortingOrder sortedEnum : values())
            if(sortedEnum.getSortedBy().equalsIgnoreCase(value)) return sortedEnum;
        throw new IllegalArgumentException();
    }
}
