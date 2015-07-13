package org.mtransit.parser.ca_saskatoon_transit_bus;

import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.Utils;
import org.mtransit.parser.gtfs.data.GCalendar;
import org.mtransit.parser.gtfs.data.GCalendarDate;
import org.mtransit.parser.gtfs.data.GRoute;
import org.mtransit.parser.gtfs.data.GSpec;
import org.mtransit.parser.gtfs.data.GStop;
import org.mtransit.parser.gtfs.data.GTrip;
import org.mtransit.parser.mt.data.MAgency;
import org.mtransit.parser.mt.data.MRoute;
import org.mtransit.parser.CleanUtils;
import org.mtransit.parser.mt.data.MTrip;

// http://opendata-saskatoon.cloudapp.net/
// http://ww9.saskatoon.ca/app/qGoogleTransit/google_transit.zip
// http://apps2.saskatoon.ca/app/data/google_transit.zip
public class SaskatoonTransitBusAgencyTools extends DefaultAgencyTools {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			args = new String[3];
			args[0] = "input/gtfs.zip";
			args[1] = "../../mtransitapps/ca-saskatoon-transit-bus-android/res/raw/";
			args[2] = ""; // files-prefix
		}
		new SaskatoonTransitBusAgencyTools().start(args);
	}

	private HashSet<String> serviceIds;

	@Override
	public void start(String[] args) {
		System.out.printf("\nGenerating Saskatoon Transit bus data...");
		long start = System.currentTimeMillis();
		this.serviceIds = extractUsefulServiceIds(args, this);
		super.start(args);
		System.out.printf("\nGenerating Saskatoon Transit bus data... DONE in %s.\n", Utils.getPrettyDuration(System.currentTimeMillis() - start));
	}

	@Override
	public boolean excludeCalendar(GCalendar gCalendar) {
		if (this.serviceIds != null) {
			return excludeUselessCalendar(gCalendar, this.serviceIds);
		}
		return super.excludeCalendar(gCalendar);
	}

	@Override
	public boolean excludeCalendarDate(GCalendarDate gCalendarDates) {
		if (this.serviceIds != null) {
			return excludeUselessCalendarDate(gCalendarDates, this.serviceIds);
		}
		return super.excludeCalendarDate(gCalendarDates);
	}

	@Override
	public boolean excludeTrip(GTrip gTrip) {
		if (this.serviceIds != null) {
			return excludeUselessTrip(gTrip, this.serviceIds);
		}
		return super.excludeTrip(gTrip);
	}

	@Override
	public Integer getAgencyRouteType() {
		return MAgency.ROUTE_TYPE_BUS;
	}

	@Override
	public long getRouteId(GRoute gRoute) {
		return Long.parseLong(gRoute.route_short_name.trim()); // using route short name as route ID
	}


	@Override
	public String getRouteShortName(GRoute gRoute) {
		return String.valueOf(Integer.parseInt(gRoute.route_short_name.trim())); // remove leading '0'
	}

	private static final String AGENCY_COLOR_BLUE = "027AA7"; // BLUE (from web site CSS)

	private static final String AGENCY_COLOR = AGENCY_COLOR_BLUE;

	@Override
	public String getAgencyColor() {
		return AGENCY_COLOR;
	}

	private static final String COLOR_DART_GREEN = "8CC63F"; // GREEN (from system map PDF)

	@Override
	public String getRouteColor(GRoute gRoute) {
		int rsn = Integer.parseInt(gRoute.route_short_name.trim());
		switch (rsn) {
		// @formatter:off
		case 1: return null;
		case 2: return null;
		case 3: return null;
		case 4: return null;
		case 5: return null;
		case 6: return null;
		case 7: return null;
		case 8: return null;
		case 9: return null;
		case 10: return null;
		case 11: return null;
		case 12: return null;
		case 13: return null;
		case 14: return null;
		case 17: return null;
		case 20: return null;
		case 21: return null;
		case 22: return null;
		case 23: return null;
		case 25: return null;
		case 26: return null;
		case 28: return null;
		case 29: return null;
		case 50: return COLOR_DART_GREEN;
		case 60: return COLOR_DART_GREEN;
		case 70: return COLOR_DART_GREEN;
		case 75: return COLOR_DART_GREEN;
		case 80: return COLOR_DART_GREEN;
		case 85: return null;
		case 100: return null;
		case 104: return null;
		case 180: return null;
		case 200: return null;
		// @formatter:on
		default:
			System.out.println("Unexpected route color " + gRoute);
			System.exit(-1);
			return null;
		}
	}

	private static final String WILDWOOD = "Wildwood";
	private static final String EXHIBITION = "Exhibition";
	private static final String HUDSON_BAY_PARK = "Hudson Bay Park";
	private static final String COLLEGE_PARK = "College Park";
	private static final String MAYFAIR = "Mayfair";
	private static final String WILLOWGROVE_SQ = "Willowgrove Sq";
	private static final String MC_CORMACK = "McCormack";
	private static final String BRIARWOOD = "Briarwood";
	private static final String _8TH_ST = "8th St";
	private static final String RIVER_HEIGHTS = "River Heights";
	private static final String AIRPORT = "Airport";
	private static final String LAWSON_HEIGHTS = "Lawson Heights";
	private static final String BROADWAY = "Broadway";
	private static final String STONEBRIDGE = "Stonebridge";
	private static final String MARKET_MALL = "Market Mall";
	private static final String MONTGOMERY = "Montgomery";
	private static final String BLAIRMORE = "Blairmore";
	private static final String NORTH_INDUSTRIAL = "North Industrial";
	private static final String UNIVERSITY_FOREST_GROVE = "University / Forest Grove";
	private static final String LAKEVIEW = "Lakeview";
	private static final String PACIFIC_HEIGHTS = "Pacific Heights";
	private static final String LAKERIDGE = "Lakeridge";
	private static final String CONFEDERATION = "Confederation";
	private static final String CITY_CTR = "City Ctr";

	@Override
	public void setTripHeadsign(MRoute mRoute, MTrip mTrip, GTrip gTrip, GSpec gtfs) {
		if (mRoute.id == 1l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(WILDWOOD, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(EXHIBITION, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 3l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(HUDSON_BAY_PARK, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(COLLEGE_PARK, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 4l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(MAYFAIR, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(WILLOWGROVE_SQ, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 5l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(MC_CORMACK, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(BRIARWOOD, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 6l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(BROADWAY, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 8l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(_8TH_ST, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 12l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(RIVER_HEIGHTS, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(AIRPORT, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 13l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(BROADWAY, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 14l) {
			if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 17l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(STONEBRIDGE, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 22l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(MONTGOMERY, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 23l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString("Hampton Village", gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(BLAIRMORE, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 25l) {
			if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(NORTH_INDUSTRIAL, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 28l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(UNIVERSITY_FOREST_GROVE, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 50l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(LAKEVIEW, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(PACIFIC_HEIGHTS, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 60l) {
			if (gTrip.direction_id == 0) {
				mTrip.setHeadsignString(LAKERIDGE, gTrip.direction_id);
				return;
			} else if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CONFEDERATION, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 75l) {
			if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 80l) {
			if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.direction_id);
				return;
			}
		} else if (mRoute.id == 85l) {
			if (gTrip.direction_id == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.direction_id);
				return;
			}
		}
		mTrip.setHeadsignString(cleanTripHeadsign(gTrip.trip_headsign), gTrip.direction_id);
	}

	private static final Pattern CENTER = Pattern.compile("(cent(er|re))", Pattern.CASE_INSENSITIVE);
	private static final String CENTER_REPLACEMENT = "Ctr";

	private static final String VIA = " via ";

	@Override
	public String cleanTripHeadsign(String tripHeadsign) {
		int indexOfVIA = tripHeadsign.toLowerCase(Locale.ENGLISH).indexOf(VIA);
		if (indexOfVIA >= 0) {
			tripHeadsign = tripHeadsign.substring(0, indexOfVIA);
		}
		tripHeadsign = CENTER.matcher(tripHeadsign).replaceAll(CENTER_REPLACEMENT);
		return CleanUtils.cleanLabel(tripHeadsign);
	}

	@Override
	public String cleanStopName(String gStopName) {
		gStopName = CleanUtils.cleanStreetTypes(gStopName);
		gStopName = CleanUtils.cleanNumbers(gStopName);
		return CleanUtils.cleanLabel(gStopName);
	}

	@Override
	public int getStopId(GStop gStop) {
		try {
			return Integer.parseInt(gStop.stop_code); // use stop code as stop ID
		} catch (Exception e) {
			System.out.println("Error while extracting stop ID from " + gStop);
			e.printStackTrace();
			System.exit(-1);
			return -1;
		}
	}
}
