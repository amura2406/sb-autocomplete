# sb-autocomplete

Demo of an autocomplete service

html can be accessed on: http://host:8080/search

## Assumptions:

1. Search operation would be triggered after 2 characters or more have been inputed to the textfield, and cannot exceed more than 30 characters.
2. It will search all company's name and will match from the beginning of every text.
3. Search results will be ordered lexicographically.
4. It will only displayed at max 15 items.

## Data Population

Please run `SearchTermPopulator.scala` first to populate the dummy data into mongodb, it is located on 'test' folder.

#### NB

If somehow there is some problem with SBT resolver when resolving jboss package expecially in IntelliJ IDEA.
Please add in these line into `~/.sbt/0.13/global.sbt`

``` sbt
resolvers += "JBoss" at "https://repository.jboss.org/"
```
