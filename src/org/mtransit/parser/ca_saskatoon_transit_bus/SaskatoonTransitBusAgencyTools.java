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
		return Long.parseLong(gRoute.getRouteShortName().trim()); // using route short name as route ID
	}


	@Override
	public String getRouteShortName(GRoute gRoute) {
		return String.valueOf(Integer.parseInt(gRoute.getRouteShortName().trim())); // remove leading '0'
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
		int rsn = Integer.parseInt(gRoute.getRouteShortName().trim());
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
		case 101: return null;
		case 102: return null;
		case 103: return null;
		case 104: return null;
		case 180: return null;
		case 200: return null;
		// @formatter:on
		default:
			System.out.printf("\nUnexpected route color %s!", gRoute);
			System.exit(-1);
			return null;
		}
	}

	private static final String WILDWOOD = "Wildwood";
	private static final String EXHIBITION = "Exhibition";
	private static final String HUDSON_BAY_PARK = "Hudson Bay Pk";
	private static final String COLLEGE_PARK = "College Pk";
	private static final String MAYFAIR = "Mayfair";
	private static final String WILLOWGROVE_SQ = "Willowgrove Sq";
	private static final String MC_CORMACK = "McCormack";
	private static final String BRIARWOOD = "Briarwood";
	private static final String _8TH_ST = "8th St";
	private static final String RIVER_HEIGHTS = "River Hts";
	private static final String AIRPORT = "Airport";
	private static final String LAWSON_HEIGHTS = "Lawson Hts";
	private static final String BROADWAY = "Broadway";
	private static final String STONEBRIDGE = "Stonebridge";
	private static final String MARKET_MALL = "Mkt Mall";
	private static final String MONTGOMERY = "Montgomery";
	private static final String BLAIRMORE = "Blairmore";
	private static final String NORTH_INDUSTRIAL = "North Ind";
	private static final String UNIVERSITY = "University";
	private static final String UNIVERSITY_FOREST_GROVE = UNIVERSITY + " / Forest Grv";
	private static final String LAKEVIEW = "Lakeview";
	private static final String PACIFIC_HEIGHTS = "Pacific Hts";
	private static final String LAKERIDGE = "Lakeridge";
	private static final String LAKEWOOD_SC = "Lakewood SC";
	private static final String CONFEDERATION = "Confederation";
	private static final String HAMPTON_VILLAGE = "Hampton Vlg";
	private static final String SOUTHEAST = "Southeast";
	private static final String DOWNTOWN = "Downtown";
	private static final String CITY_CTR = "City Ctr";

	@Override
	public void setTripHeadsign(MRoute mRoute, MTrip mTrip, GTrip gTrip, GSpec gtfs) {
		if (mRoute.getId() == 1l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(WILDWOOD, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(EXHIBITION, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 3l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(HUDSON_BAY_PARK, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(COLLEGE_PARK, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 4l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(MAYFAIR, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(WILLOWGROVE_SQ, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 5l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(MC_CORMACK, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(BRIARWOOD, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 6l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(BROADWAY, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 8l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(_8TH_ST, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 12l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(RIVER_HEIGHTS, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(AIRPORT, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 13l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(BROADWAY, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 14l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 17l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(STONEBRIDGE, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 22l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(MONTGOMERY, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 23l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(HAMPTON_VILLAGE, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(BLAIRMORE, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 25l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(NORTH_INDUSTRIAL, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 26l) {
			if (gTrip.getDirectionId() == 0 && "769407".equals(gTrip.getTripId()) && "University".equals(gTrip.getTripHeadsign())) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 28l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY_FOREST_GROVE, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 50l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(LAKEVIEW, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(PACIFIC_HEIGHTS, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 60l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(LAKERIDGE, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CONFEDERATION, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 75l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 80l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 85l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 100l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(DOWNTOWN, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(SOUTHEAST, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 101l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(LAKERIDGE, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 102l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(LAKEWOOD_SC, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 103l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
				return;
			}
		}
		mTrip.setHeadsignString(cleanTripHeadsign(gTrip.getTripHeadsign()), gTrip.getDirectionId());
	}

	private static final String VIA = " via ";

	private static final Pattern INDUSTRIAL = Pattern.compile("((^|\\W){1}(industrial)(\\W|$){1})", Pattern.CASE_INSENSITIVE);
	private static final String INDUSTRIAL_REPLACEMENT = "$2Ind$4";

	@Override
	public String cleanTripHeadsign(String tripHeadsign) {
		int indexOfVIA = tripHeadsign.toLowerCase(Locale.ENGLISH).indexOf(VIA);
		if (indexOfVIA >= 0) {
			tripHeadsign = tripHeadsign.substring(0, indexOfVIA);
		}
		tripHeadsign = INDUSTRIAL.matcher(tripHeadsign).replaceAll(INDUSTRIAL_REPLACEMENT);
		tripHeadsign = CleanUtils.cleanStreetTypes(tripHeadsign);
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
			return Integer.parseInt(gStop.getStopCode()); // use stop code as stop ID
		} catch (Exception e) {
			System.out.println("Error while extracting stop ID from " + gStop);
			e.printStackTrace();
			System.exit(-1);
			return -1;
		}
	}
}
