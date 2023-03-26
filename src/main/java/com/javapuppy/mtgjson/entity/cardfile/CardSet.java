package com.javapuppy.mtgjson.entity.cardfile;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CardSet {
    private List<Card> cards = new ArrayList<>();
    private Integer baseSetSize;
    private String block;
    private Integer cardsphereSetId;
    private String code;
    private String codeV3;
    private Boolean isForeignOnly;
    private Boolean isFoilOnly;
    private Boolean isNonFoilOnly;
    private Boolean isOnlineOnly;
    private Boolean isPaperOnly;
    private String keyruneCode;
    private String[] languages;
    private Integer mcmId;
    private Integer mcmIdExtras;
    private String mcmName;
    private String mtgoCode;
    private String name;
    private String parentCode;
    private String releaseDate;
    private Integer tcgplayerGroupId;
    private String tokenSetCode;
    private Integer totalSetSize;
    private String type;

}
