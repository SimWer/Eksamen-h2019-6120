# Eksamen-h2019-6120
Eksamen i emnet "6120 Applikasjonsutvikling for mobile enheter" høst 2019

Applikasjonens hensikt er å vise en bruker data fra mattilsyntes "smilefjes"-ordning. <br>
Dette skal vises i listeform med mulighet for mer informasjon om brukeren klikker på et gitt objekt.
Tilkoblingen til databasen skjer via et REST-api fra difi.


# 1	Tankegang
# 1.1	Design

Designet er utformet etter Googles Material Design prinsipper, på den måten får man en intuitiv app som er lett å bruke. Jeg har implementert MaterialComponents i appen, så den følger det nyeste designet til google. 
# Søk:
Søkingen er laget med en input rett under navigasjonen, og på denne måten blir ikke den i veien for resten av innholdet. Brukeren søker ved å skrive inn ønsket søk.
# Filtrering:
Det er en egen filtreringsknapp som brukeren kan klikke på, denne er plassert ved siden av søkevinduet for å lett finne frem til. Den har valg på årstallene som ligger i databasen. Brukeren kan velge filtrering før søk og dermed søke med valgt filtrering.
# Sortering:
Dette var ikke et krav, men jeg følte det var nødvendig for bedre brukervennlighet. Brukeren kan etter søk velge å sortere innholdet på enten navn stigende, navn synkende, karakter stigende eller karakter synkende.
# Visning av tilsyn:
Her har jeg valgt å vise tilsynene i form av en liste i et RecyclerView, listen er omringet av MaterialCardView for finere design. Listen kan «swipes» om ønsket.
# Detaljer visning:
Når brukeren velger et tilsyn får den en detaljert visning om det tilsynet. Her får den opp en kort informasjon boks (designet selv) om tilsynet, og en detaljert visning over kravpunkt på tilsynet. Dette vises i en enkel ListView.
# Innstillinger:
Brukeren kan legge til fast informasjon om by eller postnummer til byen. Brukeren kan også velge om informasjonen skal lastes på oppstart.
# Geolokasjon:
Brukeren kan velge å søke tilsyn basert på sin egen posisjon, dette valget er plassert i navigasjonen for lett å kunne nås.
 
# 1.2	Navigering

Jeg har valgt en topp-navigasjon for at navigasjonen skal være gjenkjennbar for alle brukere. Måten jeg har løst det på er å følge Googles Material Design prinsipper. 
På Material.io står det følgende:
«The top app bar provides content and actions related to the current screen. It’s used for branding, screen titles, navigation, and actions.» 
Basert på det viser navigasjonen ikoner fra googles egne bibliotek, sortert på mest brukte fra venstre i navigasjonen. Se bilde under.

# 2	Brukerveiledning
# 2.1	Navigering
På bildet t.h. kan du se navigasjonen, den er enkel med kun to knapper. Knappen for innstillinger er til høyre og knappen for posisjon er til venstre. Klikker du på posisjon vil søket gjennomføres basert på hvor du er plassert med mobilen din.
Resultatet kan du se på bildet under. Figur 3 Søk basert på posisjon 

På navigasjonsmenyer kan du se to knapper for filtrering og sortering. Trykk på disse for å filtrere eller sortere søket ditt (se bildene forrige side). Etter du har valgt enten sortering eller filtrering vil du få opp elementer som viser valget ditt. Disse kan krysses ut for å fjerne valget. Se bildet under.

# 2.2	Fjerning og detaljert visning

På listen som vises etter søk gir deg mulighet til å fjerne tilsyn som du ikke ønsker skal være med i visningen. Det gjøres enkelt ved å dra tilsynet enten til høyre eller venstre, du vil da få en melding om du virkelig ønsker å fjerne det. Du kan deretter velge avbryt og tilsynet vil ikke fjernes. 
Se bildet under.

Du som bruker kan velg et tilsyn i listen for å vise detaljer om det tilsynet. Det gjøres enkelt ved at du klikker på ønsket tilsyn, og du blir da sendt videre til en detaljert visning av det tilsynet, du ser også et ikon for resultat på hvert enkelt punkt. Streken viser tilsyn som ikke er vurdert. Se bildet over.
 
# 2.3	Innstillinger og liggende format

Om ønskelig kan du som bruker velge å lagre visse favorittdetaljer i applikasjonen som huskes mellom hver gang du bruker applikasjonen. Du kan også velge om denne lagrede informasjonen skal vises når du starter applikasjonen eller ikke. Se bildet under

På bildet over til høyre kan du ser applikasjonen i liggende format. Her slipper du å gå fram og tilbake mellom søking og detaljert liste. Du klikker på rader til venstre og vil få opp detaljert visning til høyre med en gang uten å måtte gå fram eller tilbake. Her kan du gjøre søk og filtrering som tidligere, og du kan også bruke posisjon.

# 3	Funksjonalitet

# a. Finne tilsyn for et spisested via navn eller poststed

Dette punktet er løst ved å sende en VolleyRequest til REST-API hos Difis Datahotell. Jeg valgte at du kan søke på hva som helst i databasen. Du kan velge å søke på navn= eller poststed=, men dette vil gi resultater som ikke er ønskelig. Da får du alle som inneholder noe av de bokstavene som er skrevet inn, og du ville ikke få det du er ute etter. Dette kunne bli løst ved å hente ut alle dataene og filtrere de i programmet, men siden den kun returnerer de hundre første resultatene, er ikke dette en måte jeg valgte å implementere det på. 
Metoden for å hente ut datasettet på er i en egen klasse som heter RestController, dataene legges så inn i en ArrayList<Tilsyn>. Hvert tilsyn i databasen legges inn som et Tilsyn-objekt. Det legges også med et id til bildet basert på total karakter til tilsynet. Alt dette gjøres kallet til databasen. 
 
Listen vises via et RecyclerView med CardView som utseendet. Dette RecyclerViewet har en egen adapterklasse som legger inn datasettet og lytter på click. Dette adapteret tar seg også av filtrering og sortering.

# b. Vise detaljinformasjon om et tilsyn

Etter valgt rad blir brukeren sendt til et nytt fragment som inneholder datasettet for kravpunkt til det aktuelle tilsynet. Dette hentes også ut via et VolleyRequest med en metode i RestController klassen. Denne metoden tar et tilsyn_id som parameter, og henter ut aktuelle kravpunkt basert på dette parameteret. Resultatene legges inn en og en i en ArrayList<Kravpunkt> som Kravpunkt-objekter. 
I toppen av fragmentet vises informasjon om tilsynet (bedrift, karakter (smilefjes-ikon, adresse og dato). Dette er laget med et egendefinert shape som en drawable ressurs. Under denne informasjonen kommer det opp en ListView som viser aktuelle kravpunkt-objekter. Denne listen fylles med data via en egendefinert adapter-klasse. Designet på listen er laget i et eget XML-ark.

# c. Tilpasse søkelisten for tilsyn

Filtrering er gjort fra adapter-klassen til tilsyn-objektet. Det gjøres ved å implementere en metode som heter getFilter(). Denne metoden returnerer et Filter som tar en String som parameter. Stringen brukes til å sortere mellom tilsynene som allerede er i RecyclerView’et. Filteret har en annen metode som heter publishResult(). Denne metoden gir med et FilterResult med den oppdaterte listen. Etter dette kaller jeg notifyOnDataSetChanged() for at listen skal oppdatere seg. 
Etter at brukeren har valgt filtrering kan det også søkes på nytt med denne filtreringen som er valgt. Dette gjøres med et nytt kall på metoden i RestController, og denne gangen legges det med filtreringen. Det kan per nå kun filtreres på årstall.
Når brukeren har valgt filtrering legges det til en Chip fra MaterialComponents. Denne er klikkbar slik at brukeren kan fjerne valget. Dette gjøres med en lytter på Chipens «closeknapp». Da oppdateres listen med det første søkte resultatet.
Brukeren kan velge å fjerne tilsyn fra listen, dette gjøres med en ItemTouchHelper som legges til RecyclerView’et. Når brukeren velger å swipe bort kommer det opp en MaterialAlertDialog med valg for enten å bekrefte eller og avbryte. Velger brukeren å bekrefte fjernes den fra både RecyclerView’et og fra Listen.

# d. Finne tilsyn/spisesteder basert på brukers posisjon (geografisk søk)

Brukeren kan velge å søke via posisjon. Dette gjøres ved å hente ut brukerens posisjonsinformasjon. Det må første spørres om tillatelse av brukeren for å hente ut posisjonen. Dette gjøres ved å sjekke om appen har tillatelse. Om den ikke har tillatelse kaller den på en egen metode for å få tak i dette. Om brukeren sier nei, vil ingenting skje. 
Om tillatelse er gitt, henter vi posisjonen til brukeren. Dette gjøres da hver gang brukeren trykker på posisjonsknappen i applikasjonen. Den spør bare om tillatelse om det ikke er gitt!
Posisjonsdataene sendes med i en VolleyRequest til kartverkets API med adresser. Her brukes det punktsøk med lengdegrad og breddegrad. Denne informasjonen brukes så til å gjennomføre et nytt søk i datahotellet. Denne gangen med postnummeret hentet fra posisjonen brukeren befinner seg på.

# e. Innstillinger/Settings/brukervalg

Innstillinger gjøres med en egen SettingActivity. Denne aktiviteten gjør ikke så mye utenom å kobles opp mot et SettingsFragment som er laget av android. Denne tar vare på informasjonen mellom hver programmkjøring og gir brukeren mulighet til å lagre data. Det er også et valg som applikasjonen sjekker ved oppstart, og det er om brukeren ønsker at denne dataen skal lastes ved oppstart. Dette er ganske enkelt og krever minimalt med koder/kommunikasjon.

# Ekstra:

Jeg har valgt å legge ved en funksjon for sortering, dette gjøres med comparator metoder basert på Tilsyn-objekter. Brukeren kan sortere på flere ting, og metodene kalles i Adapterklassen for å oppdatere RecyclerViewet med sorteringen.
Jeg har brukt litt tid på design for å gjøre appen brukervennlig, jeg har også fulgt prinsipper funnet på material.io.

# Kjøring: 

Appen kan kjøres om den er, det trengs ikke å gjøre noe spesielt for å få den i gang. Siden det ikke er login eller brukere o.l.

# 4	Kilder:
Singleton: https://developer.android.com/training/volley/requestqueue
Farger: https://stackoverflow.com/questions/27965662/how-can-i-change-default-dialog-button-text-color-in-android-5
Sortering: https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/ 
Meny: https://www.material.io/develop/android/components/menu/
Sortering/Filtreringsdialog: https://acomputerengineer.wordpress.com/2018/07/23/display-list-in-alertdialog-in-androidsimple-list-radio-button-list-check-box-list/
Filtreringsmetoder: https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/
Comparator: https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
Preferences: https://stackoverflow.com/questions/18414267/switchpreference-and-checkboxpreference-in-code


