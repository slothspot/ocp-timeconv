package name.dmitrym.timeconv

import java.io.{File, FilenameFilter, FileOutputStream}
import java.time.{Instant, ZonedDateTime, ZoneId}

import scala.io.Source

object TimeConv {
  val singleZoneCounties = Map(
    //"A1" -> "", // Anonymous Proxy
    //"A2" -> "", // Satellite Provider
    "AD" -> "Europe/Andorra",
    "AN" -> "Europe/Andorra",
    "AE" -> "Asia/Dubai",
    "AF" -> "Asia/Kabul",
    "AG" -> "America/Antigua",
    "AI" -> "America/Anguilla",
    "AL" -> "Europe/Tirane",
    "AM" -> "Asia/Yerevan",
    "AO" -> "Africa/Luanda",
    //"AP" -> "", // Asia/Pacific region
    "AR" -> "America/Argentina/Buenos_Aires",
    "AS" -> "Pacific/Pago_Pago",
    "AT" -> "Europe/Vienna",
    "AW" -> "America/Aruba",
    "AX" -> "Europe/Mariehamn",
    "AZ" -> "Asia/Baku",
    "BA" -> "Europe/Sarajevo",
    "BB" -> "America/Barbados",
    "BD" -> "Asia/Dhaka",
    "BE" -> "Europe/Brussels",
    "BF" -> "Africa/Ouagadougou",
    "BG" -> "Europe/Sofia",
    "BH" -> "Asia/Bahrain",
    "BI" -> "Africa/Bujumbura",
    "BJ" -> "Africa/Porto-Novo",
    "BM" -> "Atlantic/Bermuda",
    "BN" -> "Asia/Brunei",
    "BO" -> "America/La_Paz",
    "BS" -> "America/Nassau",
    "BT" -> "Asia/Thimphu",
    "BW" -> "Africa/Gaborone",
    "BY" -> "Europe/Minsk",
    "BZ" -> "America/Belize",
    "CD" -> "Africa/Kinshasa",
    "CF" -> "Africa/Bangui",
    "CG" -> "Africa/Brazzaville",
    "CH" -> "Europe/Zurich",
    "CI" -> "Africa/Abidjan",
    "CK" -> "Pacific/Rarotonga",
    "CL" -> "America/Santiago",
    "CM" -> "Africa/Douala",
    "CN" -> "Asia/Shanghai",
    "CO" -> "America/Bogota",
    "CR" -> "America/Costa_Rica",
    "CV" -> "Atlantic/Cape_Verde",
    "CY" -> "Asia/Nicosia",
    "CZ" -> "Europe/Prague",
    "DE" -> "Europe/Berlin",
    "DJ" -> "Africa/Djibouti",
    "DK" -> "Europe/Copenhagen",
    "DM" -> "America/Dominica",
    "DO" -> "America/Santo_Domingo",
    "DZ" -> "Africa/Algiers",
    "EC" -> "America/Guayaquil",
    "EE" -> "Europe/Tallinn",
    "EG" -> "Africa/Cairo",
    "ER" -> "Africa/Asmara",
    "ES" -> "Europe/Madrid",
    "ET" -> "Africa/Addis_Ababa",
    "EU" -> "CET",
    "FI" -> "Europe/Helsinki",
    "FJ" -> "Pacific/Fiji",
    "FK" -> "Atlantic/Stanley",
    "FM" -> "Pacific/Pohnpei",
    "FO" -> "Atlantic/Faroe",
    "FR" -> "Europe/Paris",
    "FX" -> "Europe/Paris",
    "GA" -> "Africa/Libreville",
    "GB" -> "Europe/London",
    "GD" -> "America/Grenada",
    "GE" -> "Asia/Tbilisi",
    "GF" -> "America/Cayenne",
    "GG" -> "Europe/Guernsey",
    "GH" -> "Africa/Accra",
    "GI" -> "Europe/Gibraltar",
    "GL" -> "America/Godthab",
    "GM" -> "Africa/Banjul",
    "GN" -> "Africa/Conakry",
    "GP" -> "America/Guadeloupe",
    "GQ" -> "Africa/Malabo",
    "GR" -> "Europe/Athens",
    "GT" -> "America/Guatemala",
    "GU" -> "Pacific/Guam",
    "GW" -> "Africa/Bissau",
    "GY" -> "America/Guyana",
    "HK" -> "Asia/Hong_Kong",
    "HN" -> "America/Tegucigalpa",
    "HR" -> "Europe/Zagreb",
    "HT" -> "America/Port-au-Prince",
    "HU" -> "Europe/Budapest",
    "ID" -> "Asia/Jakarta",
    "IE" -> "Europe/Dublin",
    "IL" -> "Asia/Jerusalem",
    "IM" -> "Europe/Isle_of_Man",
    "IN" -> "Asia/Kolkata",
    "IO" -> "Indian/Chagos",
    "IQ" -> "Asia/Baghdad",
    "IS" -> "Atlantic/Reykjavik",
    "IT" -> "Europe/Rome",
    "JE" -> "Europe/Jersey",
    "JM" -> "America/Jamaica",
    "JO" -> "Asia/Amman",
    "JP" -> "Asia/Tokyo",
    "KE" -> "Africa/Nairobi",
    "KG" -> "Asia/Bishkek",
    "KH" -> "Asia/Phnom_Penh",
    "KI" -> "Pacific/Enderbury",
    "KM" -> "Indian/Comoro",
    "KN" -> "America/St_Kitts",
    "KR" -> "Asia/Seoul",
    "KW" -> "Asia/Kuwait",
    "KY" -> "America/Cayman",
    "KZ" -> "Asia/Almaty",
    "LA" -> "Asia/Vientiane",
    "LB" -> "Asia/Beirut",
    "LC" -> "America/St_Lucia",
    "LI" -> "Europe/Vaduz",
    "LK" -> "Asia/Colombo",
    "LR" -> "Africa/Monrovia",
    "LS" -> "Africa/Maseru",
    "LT" -> "Europe/Vilnius",
    "LU" -> "Europe/Luxembourg",
    "LV" -> "Europe/Riga",
    "LY" -> "Africa/Tripoli",
    "MA" -> "Africa/Casablanca",
    "MC" -> "Europe/Monaco",
    "MD" -> "Europe/Chisinau",
    "ME" -> "Europe/Podgorica",
    "MF" -> "America/Marigot",
    "MG" -> "Indian/Antananarivo",
    "MH" -> "Pacific/Majuro",
    "MK" -> "Europe/Skopje",
    "ML" -> "Africa/Bamako",
    "MN" -> "Asia/Ulaanbaatar",
    "MO" -> "Asia/Macau",
    "MP" -> "Pacific/Saipan",
    "MQ" -> "America/Martinique",
    "MR" -> "Africa/Nouakchott",
    "MS" -> "America/Montserrat",
    "MT" -> "Europe/Malta",
    "MU" -> "Indian/Mauritius",
    "MV" -> "Indian/Maldives",
    "MW" -> "Africa/Blantyre",
    "MY" -> "Asia/Kuala_Lumpur",
    "MZ" -> "Africa/Maputo",
    "NA" -> "Africa/Windhoek",
    "NC" -> "Pacific/Noumea",
    "NE" -> "Africa/Niamey",
    "NG" -> "Africa/Lagos",
    "NI" -> "America/Managua",
    "NL" -> "Europe/Amsterdam",
    "NO" -> "Europe/Oslo",
    "NP" -> "Asia/Kathmandu",
    "NR" -> "Pacific/Nauru",
    "NU" -> "Pacific/Niue",
    "NZ" -> "Pacific/Auckland",
    "OM" -> "Asia/Muscat",
    "PA" -> "America/Panama",
    "PE" -> "America/Lima",
    "PF" -> "Pacific/Tahiti",
    "PG" -> "Pacific/Port_Moresby",
    "PH" -> "Asia/Manila",
    "PK" -> "Asia/Karachi",
    "PL" -> "Europe/Warsaw",
    "PM" -> "America/Miquelon",
    "PR" -> "America/Puerto_Rico",
    "PS" -> "Asia/Hebron",
    "PT" -> "Europe/Lisbon",
    "PW" -> "Pacific/Palau",
    "PY" -> "America/Asuncion",
    "QA" -> "Asia/Qatar",
    "RE" -> "Indian/Reunion",
    "RO" -> "Europe/Bucharest",
    "RS" -> "Europe/Belgrade",
    "RW" -> "Africa/Kigali",
    "SA" -> "Asia/Riyadh",
    "SB" -> "Pacific/Guadalcanal",
    "SC" -> "Indian/Mahe",
    "SE" -> "Europe/Stockholm",
    "SG" -> "Asia/Singapore",
    "SI" -> "Europe/Ljubljana",
    "SK" -> "Europe/Bratislava",
    "SL" -> "Africa/Freetown",
    "SM" -> "Europe/San_Marino",
    "SN" -> "Africa/Dakar",
    "SO" -> "Africa/Mogadishu",
    "SR" -> "America/Paramaribo",
    "ST" -> "Africa/Sao_Tome",
    "SV" -> "America/El_Salvador",
    "SZ" -> "Africa/Mbabane",
    "TC" -> "America/Grand_Turk",
    "TD" -> "Africa/Ndjamena",
    "TG" -> "Africa/Lome",
    "TH" -> "Asia/Bangkok",
    "TJ" -> "Asia/Dushanbe",
    "TL" -> "Asia/Dili",
    "TM" -> "Asia/Ashgabat",
    "TN" -> "Africa/Tunis",
    "TO" -> "Pacific/Tongatapu",
    "TR" -> "Europe/Istanbul",
    "TT" -> "America/Port_of_Spain",
    "TV" -> "Pacific/Funafuti",
    "TW" -> "Asia/Taipei",
    "TZ" -> "Africa/Dar_es_Salaam",
    "UA" -> "Europe/Kiev",
    "UG" -> "Africa/Kampala",
    "UY" -> "America/Montevideo",
    "UZ" -> "Asia/Tashkent",
    "VA" -> "Europe/Vatican",
    "VC" -> "America/St_Vincent",
    "VE" -> "America/Caracas",
    "VG" -> "America/Tortola",
    "VI" -> "America/St_Thomas",
    "VN" -> "Asia/Ho_Chi_Minh",
    "VU" -> "Pacific/Efate",
    "WS" -> "Pacific/Apia",
    "YE" -> "Asia/Aden",
    "YT" -> "Indian/Mayotte",
    "ZA" -> "Africa/Johannesburg",
    "ZM" -> "Africa/Lusaka",
    "ZW" -> "Africa/Harare"
    )

  val multiZoneCountries = Map(
    ("AU","01") -> "Australia/ACT", // ACT
    ("AU","02") -> "Australia/NSW", // NSW
    ("AU","03") -> "Australia/North", // NT
    ("AU","04") -> "Australia/Queensland", // QLD
    ("AU","05") -> "Australia/South", // SA
    ("AU","06") -> "Australia/Tasmania", // TAS
    ("AU","07") -> "Australia/Victoria", // VIC
    ("AU","08") -> "Australia/West", // WA
    ("BR","01") -> "Brazil/Acre", // BR01: Acre State, Brazil
    ("BR","02") -> "Brazil/East", // BR02: Alagoas State, Brazil
    ("BR","03") -> "Brazil/East", // BR03: Amapá State, Brazil
    ("BR","04") -> "Brazil/West", // BR04: Amazonas State, Brazil
    ("BR","05") -> "Brazil/East", // BR05: Bahia State, Brazil
    ("BR","06") -> "Brazil/East", // BR06: Ceará State, Brazil
    ("BR","07") -> "Brazil/East", // BR07: Distrito Federal Federal District, Brazil
    ("BR","08") -> "Brazil/East", // BR08: Espírito Santo State, Brazil

    ("BR","11") -> "Brazil/West", // BR11: Mato Grosso do Sul State, Brazil

    ("BR","13") -> "Brazil/East", // BR13: Maranhão State, Brazil
    ("BR","14") -> "Brazil/West", // BR14: Mato Grosso State, Brazil
    ("BR","15") -> "Brazil/East", // BR15: Minas Gerais State, Brazil
    ("BR","16") -> "Brazil/East", // BR16: Pará State, Brazil
    ("BR","17") -> "Brazil/East", // BR17: Paraíba State, Brazil
    ("BR","18") -> "Brazil/East", // BR18: Paraná State, Brazil

    ("BR","20") -> "Brazil/East", // BR20: Piauí State, Brazil
    ("BR","21") -> "Brazil/East", // BR21: Rio de Janeiro State, Brazil
    ("BR","22") -> "Brazil/East", // BR22: Rio Grande do Norte State, Brazil
    ("BR","23") -> "Brazil/East", // BR23: Rio Grande do Sul State, Brazil
    ("BR","24") -> "Brazil/West", // BR24: Rondônia State, Brazil
    ("BR","25") -> "Brazil/West", // BR25: Roraima State, Brazil
    ("BR","26") -> "Brazil/East", // BR26: Santa Catarina State, Brazil
    ("BR","27") -> "Brazil/East", // BR27: São Paulo State, Brazil
    ("BR","28") -> "Brazil/East", // BR28: Sergipe State, Brazil
    ("BR","29") -> "Brazil/East", // BR29: Goiás State, Brazil
    ("BR","30") -> "Brazil/East", // BR30: Pernambuco State, Brazil
    ("BR","31") -> "Brazil/East", // BR31: Tocantins State, Brazil
    ("CA","AB") -> "Canada/Mountain",
    ("CA","BC") -> "Canada/Pacific",
    ("CA","MB") -> "Canada/Central",
    ("CA","NB") -> "Canada/Atlantic",
    ("CA","NL") -> "Canada/Newfoundland",
    ("CA","NS") -> "Canada/Atlantic",
    ("CA","NT") -> "Canada/Mountain",
    ("CA","NU") -> "Canada/Central",
    ("CA","ON") -> "Canada/Eastern",
    ("CA","PE") -> "Canada/Atlantic",
    ("CA","QC") -> "Canada/Eastern",
    ("CA","SK") -> "Canada/Saskatchewan",
    ("CA","YT") -> "Canada/Yukon",

    ("MX","23") -> "US/Eastern", // Quintana Roo

    ("MX","03") -> "US/Mountain", // Baja California Sur
    ("MX","06") -> "US/Mountain", // Chihuahua State, Mexico
    ("MX","18") -> "US/Mountain", // Nayarit
    ("MX","25") -> "US/Mountain", // Sinaloa
    ("MX","26") -> "US/Mountain", // Sonora

    ("MX","02") -> "US/Pacific", // Baja California

    ("MX","01") -> "US/Central", // Aguascalientes
    ("MX","04") -> "US/Central", // Campeche
    ("MX","05") -> "US/Central", // Chiapas
    ("MX","07") -> "US/Central", // Coahuila
    ("MX","08") -> "US/Central", // Colima
    ("MX","09") -> "US/Central", // Distrito Federal Federal District, Mexico
    ("MX","10") -> "US/Central", // Durango
    ("MX","11") -> "US/Central", // Guanajuato
    ("MX","12") -> "US/Central", // Guerrero
    ("MX","13") -> "US/Central", // Hidalgo State, Mexico
    ("MX","14") -> "US/Central", // Jalisco
    ("MX","15") -> "US/Central", // México State, Mexico
    ("MX","16") -> "US/Central", // Michoacán
    ("MX","17") -> "US/Central", // Morelos
    ("MX","19") -> "US/Central", // Nuevo León
    ("MX","20") -> "US/Central", // Oaxaca
    ("MX","21") -> "US/Central", // Puebla
    ("MX","22") -> "US/Central", // Querétaro
    ("MX","24") -> "US/Central", // San Luis Potosí
    ("MX","27") -> "US/Central", // Tabasco
    ("MX","28") -> "US/Central", // Tamaulipas
    ("MX","29") -> "US/Central", // Tlaxcala
    ("MX","30") -> "US/Central", // Veracruz
    ("MX","31") -> "US/Central", // Yucatán
    ("MX","32") -> "US/Central", // Zacatecas

    ("RU","23") -> "Europe/Kaliningrad", //,"Kaliningrad"

    ("RU","07") -> "Europe/Samara", //,"Astrakhan'"
    ("RU","65") -> "Europe/Samara", //,"Samara"
    ("RU","80") -> "Europe/Samara", //,"Udmurt"
    ("RU","81") -> "Europe/Samara", //,"Ul'yanovsk"

    ("RU","08") -> "Asia/Yekaterinburg", //,"Bashkortostan"
    ("RU","13") -> "Asia/Yekaterinburg", //,"Chelyabinsk"
    ("RU","32") -> "Asia/Yekaterinburg", //,"Khanty-Mansiy"
    ("RU","40") -> "Asia/Yekaterinburg", //,"Kurgan"
    ("RU","55") -> "Asia/Yekaterinburg", //,"Orenburg"
    ("RU","58") -> "Asia/Yekaterinburg", //,"Perm'"
    ("RU","90") -> "Asia/Yekaterinburg", //,"Permskiy Kray"
    ("RU","71") -> "Asia/Yekaterinburg", //,"Sverdlovsk"
    ("RU","78") -> "Asia/Yekaterinburg", //,"Tyumen'"
    ("RU","87") -> "Asia/Yekaterinburg", //,"Yamal-Nenets"

    ("RU","54") -> "Asia/Omsk", //,"Omsk"

    ("RU","04") -> "Asia/Krasnoyarsk", //,"Altaisky krai"
    ("RU","03") -> "Asia/Krasnoyarsk", //,"Gorno-Altay"
    ("RU","29") -> "Asia/Krasnoyarsk", //,"Kemerovo"
    ("RU","31") -> "Asia/Krasnoyarsk", //,"Khakass"
    ("RU","39") -> "Asia/Krasnoyarsk", //,"Krasnoyarsk"
    ("RU","91") -> "Asia/Krasnoyarsk", //,"Krasnoyarskiy Kray"
    ("RU","53") -> "Asia/Krasnoyarsk", //,"Novosibirsk"
    ("RU","75") -> "Asia/Krasnoyarsk", //,"Tomsk"
    ("RU","79") -> "Asia/Krasnoyarsk", //,"Tuva"

    ("RU","20") -> "Asia/Irkutsk", //,"Irkutsk"
    ("RU","11") -> "Asia/Irkutsk", //,"Buryat"

    ("RU","05") -> "Asia/Yakutsk", //,"Amur"
    ("RU","93") -> "Asia/Yakutsk", //,"Zabaykal'skiy Kray"
    ("RU","63") -> "Asia/Yakutsk", //,"Sakha"

    ("RU","89") -> "Asia/Vladivostok", //,"Yevrey"
    ("RU","30") -> "Asia/Vladivostok", //,"Khabarovsk"
    ("RU","59") -> "Asia/Vladivostok", //,"Primor'ye"

    ("RU","44") -> "Asia/Magadan", //,"Magadan"
    ("RU","64") -> "Asia/Magadan", //,"Sakhalin"

    ("RU","15") -> "Asia/Kamchatka", //,"Chukot"
    ("RU","26") -> "Asia/Kamchatka", //,"Kamchatka"
    ("RU","92") -> "Asia/Kamchatka", //,"Kamchatskiy Kray"

    ("RU","01") -> "Europe/Moscow", //,"Adygeya, Republic of"
    ("RU","02") -> "Europe/Moscow", //,"Aginsky Buryatsky AO"
    ("RU","06") -> "Europe/Moscow", //,"Arkhangel'sk"
    ("RU","09") -> "Europe/Moscow", //,"Belgorod"
    ("RU","10") -> "Europe/Moscow", //,"Bryansk"
    ("RU","12") -> "Europe/Moscow", //,"Chechnya"
    ("RU","14") -> "Europe/Moscow", //,"Chita"
    ("RU","16") -> "Europe/Moscow", //,"Chuvashia"
    ("RU","17") -> "Europe/Moscow", //,"Dagestan"
    ("RU","18") -> "Europe/Moscow", //,"Evenk"
    ("RU","19") -> "Europe/Moscow", //,"Ingush"
    ("RU","21") -> "Europe/Moscow", //,"Ivanovo"
    ("RU","22") -> "Europe/Moscow", //,"Kabardin-Balkar"
    ("RU","24") -> "Europe/Moscow", //,"Kalmyk"
    ("RU","25") -> "Europe/Moscow", //,"Kaluga"
    ("RU","27") -> "Europe/Moscow", //,"Karachay-Cherkess"
    ("RU","28") -> "Europe/Moscow", //,"Karelia"
    ("RU","33") -> "Europe/Moscow", //,"Kirov"
    ("RU","34") -> "Europe/Moscow", //,"Komi"
    ("RU","36") -> "Europe/Moscow", //,"Koryak"
    ("RU","37") -> "Europe/Moscow", //,"Kostroma"
    ("RU","38") -> "Europe/Moscow", //,"Krasnodar"
    ("RU","41") -> "Europe/Moscow", //,"Kursk"
    ("RU","42") -> "Europe/Moscow", //,"Leningrad"
    ("RU","43") -> "Europe/Moscow", //,"Lipetsk"
    ("RU","45") -> "Europe/Moscow", //,"Mariy-El"
    ("RU","46") -> "Europe/Moscow", //,"Mordovia"
    ("RU","47") -> "Europe/Moscow", //,"Moskva"
    ("RU","48") -> "Europe/Moscow", //,"Moscow City"
    ("RU","49") -> "Europe/Moscow", //,"Murmansk"
    ("RU","50") -> "Europe/Moscow", //,"Nenets"
    ("RU","51") -> "Europe/Moscow", //,"Nizhegorod"
    ("RU","52") -> "Europe/Moscow", //,"Novgorod"
    ("RU","56") -> "Europe/Moscow", //,"Orel"
    ("RU","57") -> "Europe/Moscow", //,"Penza"
    ("RU","60") -> "Europe/Moscow", //,"Pskov"
    ("RU","61") -> "Europe/Moscow", //,"Rostov"
    ("RU","62") -> "Europe/Moscow", //,"Ryazan'"
    ("RU","66") -> "Europe/Moscow", //,"Saint Petersburg City"
    ("RU","67") -> "Europe/Moscow", //,"Saratov"
    ("RU","68") -> "Europe/Moscow", //,"North Ossetia"
    ("RU","69") -> "Europe/Moscow", //,"Smolensk"
    ("RU","70") -> "Europe/Moscow", //,"Stavropol'"
    ("RU","72") -> "Europe/Moscow", //,"Tambovskaya oblast"
    ("RU","73") -> "Europe/Moscow", //,"Tatarstan"
    ("RU","74") -> "Europe/Moscow", //,"Taymyr"
    ("RU","76") -> "Europe/Moscow", //,"Tula"
    ("RU","77") -> "Europe/Moscow", //,"Tver'"
    ("RU","83") -> "Europe/Moscow", //,"Vladimir"
    ("RU","84") -> "Europe/Moscow", //,"Volgograd"
    ("RU","85") -> "Europe/Moscow", //,"Vologda"
    ("RU","86") -> "Europe/Moscow", //,"Voronezh"
    ("RU","88") -> "Europe/Moscow", //,"Yaroslavl'"

    //("US","AA") -> "US/Central", // US Military, America
    //("US","AP") -> "", // US Military, Pacific
    //("US","AE") -> "", // US Military, Other
    ("US","AK") -> "US/Alaska",
    ("US","AL") -> "US/Central",
    ("US","AR") -> "US/Central",
    ("US","AZ") -> "US/Mountain",
    ("US","CA") -> "US/Pacific",
    ("US","CO") -> "US/Mountain",
    ("US","CT") -> "US/Eastern",
    ("US","DC") -> "US/Eastern",
    ("US","DE") -> "US/Eastern",
    ("US","FL") -> "US/Eastern",
    ("US","HI") -> "US/Hawaii",
    ("US","IA") -> "US/Central",
    ("US","ID") -> "US/Mountain",
    ("US","IL") -> "US/Central",
    ("US","IN") -> "US/Eastern",
    ("US","GA") -> "US/Eastern",
    ("US","KS") -> "US/Central",
    ("US","KY") -> "US/Eastern",
    ("US","LA") -> "US/Central",
    ("US","MA") -> "US/Eastern",
    ("US","MD") -> "US/Eastern",
    ("US","ME") -> "US/Eastern",
    ("US","MI") -> "US/Eastern",
    ("US","MN") -> "US/Central",
    ("US","MO") -> "US/Central",
    ("US","MS") -> "US/Central",
    ("US","MT") -> "US/Mountain",
    ("US","NC") -> "US/Eastern",
    ("US","ND") -> "US/Central",
    ("US","NE") -> "US/Central",
    ("US","NH") -> "US/Eastern",
    ("US","NJ") -> "US/Eastern",
    ("US","NM") -> "US/Mountain",
    ("US","NV") -> "US/Pacific",
    ("US","NY") -> "US/Eastern",
    ("US","OH") -> "US/Eastern",
    ("US","OK") -> "US/Central",
    ("US","OR") -> "US/Pacific",
    ("US","PA") -> "US/Eastern",
    ("US","RI") -> "US/Eastern",
    ("US","SC") -> "US/Eastern",
    ("US","SD") -> "US/Central",
    ("US","TN") -> "US/Central",
    ("US","TX") -> "US/Central",
    ("US","UT") -> "US/Mountain",
    ("US","VA") -> "US/Eastern",
    ("US","VT") -> "US/Eastern",
    ("US","WA") -> "US/Pacific",
    ("US","WI") -> "US/Central",
    ("US","WV") -> "US/Eastern",
    ("US","WY") -> "US/Mountain"
    )

  def timezone(country: String, state: Option[String]):ZoneId = {
    if(singleZoneCounties.contains(country)) {
      ZoneId.of(singleZoneCounties(country))
    } else if(state.isDefined && multiZoneCountries.contains( (country, state.get) )) {
      ZoneId.of(multiZoneCountries( (country, state.get) ))
    } else {
      println(s"Mapping not found for '$country' -> '$state'")
      ZoneId.of("UTC")
    }
  }
  def process(path: String):Unit = {
    println(s"Processing $path")
    val src = Source.fromFile(path)
    val lines = src.getLines
    val header = lines.take(1).next
    val lastSep = path.lastIndexOf("/")
    val dir = path.substring(0, lastSep + 1)
    val fileName = path.substring(lastSep + 1)
    val fos = new FileOutputStream(dir + "mod_" + fileName)
    fos.write((header + ",conv_time\n").getBytes)

    lines.foreach{l =>
      val (ts, country, state) = l.split(",") match {
        case Array(ts, loc) => loc.split(">") match {
          case Array(country) => (ts, country, None)
          case Array(country, state) => (ts, country, Some(state))
          case Array(country, state, _) => (ts, country, Some(state))
        }
      }
      val tz = timezone(country, state)
      val dateUTC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ts.toLong), ZoneId.of("UTC"))
      val dateLocal = dateUTC.withZoneSameInstant(tz)
      fos.write( (l + "," + dateLocal + "\n").getBytes)
    }
    fos.close()
  }
  def main(args: Array[String]):Unit = {
    val basePath = if(args.isEmpty) {
      "/Users/dmitry/Projects/kaggle-outbrain-click/ev_ts_geo.csv/"
    } else {
      args(0)
    }
    val f = new File(basePath)
    if(f.exists) {
      val filesList = if(f.isFile) {
        Seq(f.getAbsolutePath)
      } else {
        f.list(new FilenameFilter{
          override def accept(dir:File, name: String) = name.endsWith(".csv")
        }).map(s => f.getAbsolutePath + File.separator + s).toSeq
      }
      filesList.foreach{ s =>
        process(s)
      }
    } else {
      println("Wrong base path")
    }
  }
}
