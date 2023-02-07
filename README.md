## MTGJSON Converter

This project requires two JSON files that are downloaded from mtgjson.com:

- Set List, located at https://mtgjson.com/downloads/all-files/#setlist
- Atomic Cards, located at https://mtgjson.com/downloads/all-files/#atomiccards

These files are too large for GitHub, so they must be downloaded and placed in the
`src/resources/com/javapuppy/mtgjson` folder, alongside Schema.json, which is
just for reference.

## Sample Output
After running CardConverter.java, a CSV file (`mtg.csv`) is produced in the top project directory. It contains a comma-delimited header row, followed by one card per row.
<pre>
title,red,green,white,black,blue,numColors,instant,artifact,planeswalker,sorcery,creature,enchantment,land,edhrecRank,reserved,reprinted,manaValue,power,toughness,firstPrintingSetName,firstPrintingSetCode,firstPrintingDate
"Black Lotus",false,false,false,false,false,0,false,true,false,false,false,false,false,0,false,true,0.000000,-1,-1,Limited Edition Alpha,LEA,1993-08-05
"Prodigal Sorcerer",false,false,false,false,true,1,false,false,false,false,true,false,false,7326,false,true,3.000000,1,1,Limited Edition Alpha,LEA,1993-08-05
"Serra Angel",false,false,true,false,false,1,false,false,false,false,true,false,false,8330,false,true,5.000000,4,4,Limited Edition Alpha,LEA,1993-08-05
"Waiting in the Weeds",false,true,false,false,false,1,false,false,false,true,false,false,false,13630,false,true,3.000000,-1,-1,Mirage,MIR,1996-10-08
</pre>

## Data Dictionary
| Field                | Data Type | Description                                                                                                                 |
|----------------------|-----------|-----------------------------------------------------------------------------------------------------------------------------|
| title                | string    | Doubled quoted title of the card in ASCII characters                                                                        |
| red                  | boolean   | True if the card has red color identity                                                                                     |
| green                | boolean   | True if the card has green color identity                                                                                   |
| white                | boolean   | True if the card has white color identity                                                                                   |
| black                | boolean   | True if the card has black color identity                                                                                   |
| blue                 | boolean   | True if the card has blue color identity                                                                                    |
| numColors            | integer   | The number of color identities of the card, 0-5                                                                             |
| instant              | boolean   | True if the card has type "Instant"                                                                                         |
| artifact             | boolean   | True if the card has type "Artifact"                                                                                        |
| planeswalker         | boolean   | True if the card has type "Planeswalker"                                                                                    |
| sorcery              | boolean   | True if the card has type "Sorcery"                                                                                         |
| creature             | boolean   | True if the card has type "Creature"                                                                                        |
| enchantment          | boolean   | True if the card has type "Enchantment"                                                                                     |
| land                 | boolean   | True if the card has type "Land"                                                                                            |
| edhrecRank           | integer   | Card rank from the EDHREC deck-analysis tool (see https://edhrec.com)                                                       |
| reserved             | boolean   | True if the card is on the M:TG reserved list (see https://magic.wizards.com/en/news/announcements/official-reprint-policy) |
| reprinted            | boolean   | True if the card has been printed in more than one card set                                                                 |
| manaValue            | float     | The mana value of the card, sometimes referred to as the "converted mana cost"                                              |
| power                | integer   | The power value of the card, or -1 if the card does not have a power value                                                  |
| toughness            | integer   | The toughness value of the card, or -1 if the card does not have a toughness value                                          |
| firstPrintingSetName | string    | The name of the set in which the card was first printed                                                                     |
| firstPrintingSetCode | string    | The code of the set in which the card was first printed                                                                     |
| firstPrintingSetDate | string    | The release date of the set in which the card was first printed, in the format YYYY-MM-DD                                   |

## Assumptions and Exclusions
Cards that do not have one of the following primary types are excluded from the output:
- Instant
- Artifact
- Planeswalker
- Sorcery
- Creature
- Enchantment
- Land