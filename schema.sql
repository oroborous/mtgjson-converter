create table mtg_cards
(
    uuid            text,
    title           text,
    color           text,
    numColors       integer,
    type            text,
    edhrecRank      integer,
    avgRetailPrice  numeric,
    avgBuylistPrice numeric,
    manaValue       numeric,
    power           text,
    toughness       text,
    setName         text,
    setCode         text,
    releaseDate     date,
    rarity          text,
    artist          text
);

create table mtg_prices
(
    uuid         text,
    retailer     text,
    priceType    text,
    "2022_11_27" numeric,
    "2022_11_28" numeric,
    "2022_11_29" numeric,
    "2022_11_30" numeric,
    "2022_12_01" numeric,
    "2022_12_02" numeric,
    "2022_12_03" numeric,
    "2022_12_04" numeric,
    "2022_12_05" numeric,
    "2022_12_06" numeric,
    "2022_12_07" numeric,
    "2022_12_08" numeric,
    "2022_12_09" numeric,
    "2022_12_10" numeric,
    "2022_12_11" numeric,
    "2022_12_12" numeric,
    "2022_12_13" numeric,
    "2022_12_14" numeric,
    "2022_12_15" numeric,
    "2022_12_16" numeric,
    "2022_12_18" numeric,
    "2022_12_19" numeric,
    "2022_12_20" numeric,
    "2022_12_21" numeric,
    "2022_12_23" numeric,
    "2022_12_24" numeric,
    "2022_12_25" numeric,
    "2022_12_26" numeric,
    "2022_12_27" numeric,
    "2022_12_28" numeric,
    "2022_12_29" numeric,
    "2022_12_30" numeric,
    "2022_12_31" numeric,
    "2023_01_01" numeric,
    "2023_01_02" numeric,
    "2023_01_03" numeric,
    "2023_01_04" numeric,
    "2023_01_05" numeric,
    "2023_01_06" numeric,
    "2023_01_07" numeric,
    "2023_01_08" numeric,
    "2023_01_09" numeric,
    "2023_01_10" numeric,
    "2023_01_11" numeric,
    "2023_01_12" numeric,
    "2023_01_13" numeric,
    "2023_01_14" numeric,
    "2023_01_15" numeric,
    "2023_01_16" numeric,
    "2023_01_17" numeric,
    "2023_01_18" numeric,
    "2023_01_19" numeric,
    "2023_01_20" numeric,
    "2023_01_21" numeric,
    "2023_01_22" numeric,
    "2023_01_23" numeric,
    "2023_01_24" numeric,
    "2023_01_25" numeric,
    "2023_01_26" numeric,
    "2023_01_27" numeric,
    "2023_01_28" numeric,
    "2023_01_29" numeric,
    "2023_01_31" numeric,
    "2023_02_01" numeric,
    "2023_02_02" numeric,
    "2023_02_03" numeric,
    "2023_02_04" numeric,
    "2023_02_05" numeric,
    "2023_02_06" numeric,
    "2023_02_07" numeric,
    "2023_02_08" numeric,
    "2023_02_09" numeric,
    "2023_02_10" numeric,
    "2023_02_11" numeric,
    "2023_02_12" numeric,
    "2023_02_13" numeric,
    "2023_02_15" numeric,
    "2023_02_16" numeric,
    "2023_02_17" numeric,
    "2023_02_18" numeric,
    "2023_02_19" numeric,
    "2023_02_20" numeric,
    "2023_02_21" numeric,
    "2023_02_22" numeric,
    "2023_02_23" numeric,
    "2023_02_24" numeric,
    "2023_02_25" numeric,
    "2023_02_26" numeric,
    "2023_02_27" numeric,
    "2023_02_28" numeric,
    "2023_03_01" numeric,
    "2023_03_02" numeric,
    "2023_03_03" numeric,
    "2023_03_04" numeric,
    "2023_03_05" numeric,
    "2023_03_06" numeric,
    "2023_03_07" numeric,
    "2023_03_08" numeric,
    "2023_03_09" numeric,
    "2023_03_10" numeric,
    "2023_03_11" numeric,
    "2023_03_12" numeric,
    "2023_03_13" numeric,
    "2023_03_14" numeric,
    "2023_03_15" numeric,
    "2023_03_16" numeric,
    "2023_03_18" numeric,
    "2023_03_19" numeric,
    "2023_03_20" numeric,
    "2023_03_21" numeric,
    "2023_03_22" numeric,
    "2023_03_23" numeric,
    "2023_03_24" numeric
);

create table mtg_sets (code text,
                      name text,
                      releaseDate date,
                      cardsphereSetId integer,
                      tcgplayerGroupId integer,
                      totalSetSize integer);

create table mtg_collections (userEmail text,
                             uuid text,
                             count integer);