package org.mtransit.parser.ca_saskatoon_transit_bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.mtransit.parser.CleanUtils;
import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.Pair;
import org.mtransit.parser.SplitUtils;
import org.mtransit.parser.SplitUtils.RouteTripSpec;
import org.mtransit.parser.Utils;
import org.mtransit.parser.gtfs.data.GCalendar;
import org.mtransit.parser.gtfs.data.GCalendarDate;
import org.mtransit.parser.gtfs.data.GRoute;
import org.mtransit.parser.gtfs.data.GSpec;
import org.mtransit.parser.gtfs.data.GStop;
import org.mtransit.parser.gtfs.data.GTrip;
import org.mtransit.parser.gtfs.data.GTripStop;
import org.mtransit.parser.mt.data.MAgency;
import org.mtransit.parser.mt.data.MDirectionType;
import org.mtransit.parser.mt.data.MRoute;
import org.mtransit.parser.mt.data.MTrip;
import org.mtransit.parser.mt.data.MTripStop;

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
		case 15: return null;
		case 17: return null;
		case 18: return null;
		case 19: return null;
		case 20: return null;
		case 21: return null;
		case 22: return null;
		case 23: return null;
		case 25: return null;
		case 26: return null;
		case 28: return null;
		case 29: return null;
		case 30: return null;
		case 35: return null;
		case 40: return null;
		case 45: return null;
		case 50: return COLOR_DART_GREEN;
		case 55: return null;
		case 60: return COLOR_DART_GREEN;
		case 65: return null;
		case 70: return COLOR_DART_GREEN;
		case 75: return null;
		case 80: return COLOR_DART_GREEN;
		case 81: return null;
		case 82: return null;
		case 83: return null;
		case 84: return null;
		case 85: return null;
		case 86: return null;
		case 100: return null;
		case 101: return null;
		case 102: return null;
		case 103: return null;
		case 104: return null;
		case 117: return null;
		case 180: return null;
		case 200: return null;
		case 305: return null;
		case 311: return null;
		case 312: return null;
		case 314: return null;
		case 315: return null;
		case 316: return null;
		case 325: return null;
		case 331: return null;
		case 332: return null;
		case 333: return null;
		case 334: return null;
		case 335: return null;
		case 336: return null;
		case 338: return null;
		case 339: return null;
		case 341: return null;
		case 342: return null;
		case 346: return null;
		case 348: return null;
		case 349: return null;
		case 352: return null;
		case 354: return null;
		case 356: return null;
		case 358: return null;
		case 514: return null;
		case 523: return null;
		// @formatter:on
		default:
			System.out.printf("\nUnexpected route color %s!\n", gRoute);
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
	private static final String LAKEVIEW = "Lakeview";
	private static final String PACIFIC_HEIGHTS = "Pacific Hts";
	private static final String LAKERIDGE = "Lakeridge";
	private static final String LAKEWOOD_SC = "Lakewood SC";
	private static final String CONFEDERATION = "Confederation";
	private static final String HAMPTON_VILLAGE = "Hampton Vlg";
	private static final String SOUTHEAST = "Southeast";
	private static final String DOWNTOWN = "Downtown";
	private static final String CITY_CTR = "City Ctr";

	private static HashMap<Long, RouteTripSpec> ALL_ROUTE_TRIPS2;
	static {
		HashMap<Long, RouteTripSpec> map2 = new HashMap<Long, RouteTripSpec>();
		map2.put(15L, new RouteTripSpec(15L, //
				MDirectionType.NORTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, "City Ctr", //
				MDirectionType.SOUTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, "Valley Road") //
				.addTripSort(MDirectionType.NORTH.intValue(), //
						Arrays.asList(new String[] { //
						"5913", // Valley Road Garage
								"3164", // 23rd Street / 2nd Avenue
								"5901", // Downtown Terminal West
						})) //
				.addTripSort(MDirectionType.SOUTH.intValue(), //
						Arrays.asList(new String[] { //
						"3164", // 23rd Street / 2nd Avenue
								"5913" // Valley Road Garage
						})) //
				.compileBothTripSort());
		map2.put(346L, new RouteTripSpec(346L, //
				MDirectionType.NORTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, "Ave W / 33rd st", //
				MDirectionType.SOUTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, "Shaw Ctr / Bowlt") //
				.addTripSort(MDirectionType.NORTH.intValue(), //
						Arrays.asList(new String[] { //
						"5465", // Bowlt / Shaw Cente
								"3913", // ++
								"4018" // Avenue W / Byers
						})) //
				.addTripSort(MDirectionType.SOUTH.intValue(), //
						Arrays.asList(new String[] { //
						"4005", // Avenue W / 33rd Street
								"4307", // ++
								"5465" // Bowlt / Shaw Center
						})) //
				.compileBothTripSort());
		map2.put(523L, new RouteTripSpec(523L, //
				MDirectionType.NORTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, HAMPTON_VILLAGE, //
				MDirectionType.SOUTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, CONFEDERATION) //
				.addTripSort(MDirectionType.NORTH.intValue(), //
						Arrays.asList(new String[] { //
						"5465", // != Bowlt / Shaw Center
								"5661", // ++
								"4169", // !=
								"3907", // ==
								"5911", // Confederation Terminal
								"4179", // ++
								"3849", // ++
								"5533" // East Hampton / McClocklin
						})) //
				.addTripSort(MDirectionType.SOUTH.intValue(), //
						Arrays.asList(new String[] { //
						"5533", // East Hampton / McClocklin
								"4283", // Junor / Carrothers
								"5767", // ++
								"4178", // !=
								// "5465", // != Bowlt / Shaw Center
								// "4169", // !=
								"3907", // ==
								"5911" // Confederation Terminal
						})) //
				.compileBothTripSort());
		ALL_ROUTE_TRIPS2 = map2;
	}

	@Override
	public int compareEarly(long routeId, List<MTripStop> list1, List<MTripStop> list2, MTripStop ts1, MTripStop ts2, GStop ts1GStop, GStop ts2GStop) {
		if (ALL_ROUTE_TRIPS2.containsKey(routeId)) {
			return ALL_ROUTE_TRIPS2.get(routeId).compare(routeId, list1, list2, ts1, ts2, ts1GStop, ts2GStop);
		}
		return super.compareEarly(routeId, list1, list2, ts1, ts2, ts1GStop, ts2GStop);
	}

	@Override
	public ArrayList<MTrip> splitTrip(MRoute mRoute, GTrip gTrip, GSpec gtfs) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return ALL_ROUTE_TRIPS2.get(mRoute.getId()).getAllTrips();
		}
		return super.splitTrip(mRoute, gTrip, gtfs);
	}

	@Override
	public Pair<Long[], Integer[]> splitTripStop(MRoute mRoute, GTrip gTrip, GTripStop gTripStop, ArrayList<MTrip> splitTrips, GSpec routeGTFS) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return SplitUtils.splitTripStop(mRoute, gTrip, gTripStop, routeGTFS, ALL_ROUTE_TRIPS2.get(mRoute.getId()));
		}
		return super.splitTripStop(mRoute, gTrip, gTripStop, splitTrips, routeGTFS);
	}

	@Override
	public void setTripHeadsign(MRoute mRoute, MTrip mTrip, GTrip gTrip, GSpec gtfs) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return; // split
		}
		if (mRoute.getId() == 3l) {
			if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 7l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("Dundonald", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 83l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("City Ctr", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 100l) {
			if (gTrip.getDirectionId() == 1) {
				if ("Downtown Direct".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(SOUTHEAST, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 101l) {
			if (gTrip.getDirectionId() == 0) {
				if ("University Direct 1".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
					return;
				}
			} else if (gTrip.getDirectionId() == 1) {
				if ("University Direct 1".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(LAKERIDGE, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 102l) {
			if (gTrip.getDirectionId() == 0) {
				if ("Stonebridge University Express".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
					return;
				}
			} else if (gTrip.getDirectionId() == 1) {
				if ("University Direct 2".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(LAKEWOOD_SC, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 103l) {
			if (gTrip.getDirectionId() == 0) {
				if ("Stonebridge University Express".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(DOWNTOWN, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 104l) {
			if (gTrip.getDirectionId() == 0) {
				if ("Willowgrove Downtown Express".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(DOWNTOWN, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 180l) {
			if (gTrip.getDirectionId() == 0) {
				if ("Kenderdine Downtown Express".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString(DOWNTOWN, gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 311l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 312l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString(MARKET_MALL, gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString(DOWNTOWN, gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 314l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 331l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 332l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 335l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 338l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 339l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 342l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 348l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 352l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		} else if (mRoute.getId() == 358l) {
			if (gTrip.getDirectionId() == 0) {
				mTrip.setHeadsignString("AM", gTrip.getDirectionId());
				return;
			} else if (gTrip.getDirectionId() == 1) {
				mTrip.setHeadsignString("PM", gTrip.getDirectionId());
				return;
			}
		}
		mTrip.setHeadsignString(cleanTripHeadsign(gTrip.getTripHeadsign()), gTrip.getDirectionId());
	}

	@Override
	public boolean mergeHeadsign(MTrip mTrip, MTrip mTripToMerge) {
		if (mTrip.getRouteId() == 1l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(WILDWOOD, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(EXHIBITION, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 3l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(HUDSON_BAY_PARK, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(COLLEGE_PARK, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 4l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(MAYFAIR, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(WILLOWGROVE_SQ, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 5l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(MC_CORMACK, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(BRIARWOOD, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 6l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(BROADWAY, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(MARKET_MALL, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 7l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 8l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(_8TH_ST, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(DOWNTOWN, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 12l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(RIVER_HEIGHTS, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(AIRPORT, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 13l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(BROADWAY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 14l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 17l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(STONEBRIDGE, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(MARKET_MALL, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 18l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 19l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString("Ctr Mall", mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 22l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(MONTGOMERY, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 23l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(HAMPTON_VILLAGE, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(BLAIRMORE, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 25l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(NORTH_INDUSTRIAL, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 26l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(MARKET_MALL, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 28l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 30l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 35l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 45l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString("Kenderdine", mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 50l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(LAKEVIEW, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(PACIFIC_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 55l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 60l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(LAKERIDGE, mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CONFEDERATION, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 70l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 75l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 80l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 81l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString("Ctr Mall", mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 82l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString("University", mTrip.getHeadsignId());
				return true;
			} else if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString("Ctr Mall", mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 84l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString("Ctr Mall", mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 85l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString(CITY_CTR, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 86l) {
			if (mTrip.getHeadsignId() == 1) {
				mTrip.setHeadsignString("Ctr Mall", mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 100l) {
			if (mTrip.getHeadsignId() == 0) {
				mTrip.setHeadsignString(DOWNTOWN, mTrip.getHeadsignId());
				return true;
			}
		}
		System.out.printf("\nUnexpected trips to merge %s and %s.\n", mTrip, mTripToMerge);
		System.exit(-1);
		return false;
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
