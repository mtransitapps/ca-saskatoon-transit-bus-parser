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
		if ("Not In Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return true;
		}
		if ("Out Of Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return true;
		}
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

	private static final String MAYFAIR = "Mayfair";
	private static final String WILLOWGROVE = "Willowgrove";
	private static final String WILLOWGROVE_SQUARE = WILLOWGROVE + " Sq";
	private static final String RIVER_HEIGHTS = "River Hts";
	private static final String AIRPORT = "Airport";
	private static final String LAWSON = "Lawson";
	private static final String LAWSON_HEIGHTS = LAWSON + " Hts";
	private static final String LAWSON_TERMINAL = LAWSON + " Terminal";
	private static final String BROADWAY = "Broadway";
	private static final String MARKET_MALL = "Mkt Mall";
	private static final String CENTRE_MALL = "Ctr Mall";
	private static final String MONTGOMERY = "Montgomery";
	private static final String BLAIRMORE = "Blairmore";
	private static final String UNIVERSITY = "University";
	private static final String LAKERIDGE = "Lakeridge";
	private static final String CONFEDERATION = "Confederation";
	private static final String CONFEDERATION_TERMINAL = CONFEDERATION + " Terminal";
	private static final String HAMPTON_VILLAGE = "Hampton Vlg";
	private static final String DOWNTOWN = "Downtown";
	private static final String CITY_CENTER = "City Ctr";
	private static final String FORESTGROVE = "Forestgrove";
	private static final String SILVERSPRING = "Silverspring";
	private static final String GARAGE = "Garage";

	private static HashMap<Long, RouteTripSpec> ALL_ROUTE_TRIPS2;
	static {
		HashMap<Long, RouteTripSpec> map2 = new HashMap<Long, RouteTripSpec>();
		map2.put(15L, new RouteTripSpec(15L, //
				MDirectionType.NORTH.intValue(), MTrip.HEADSIGN_TYPE_STRING, CITY_CENTER, //
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
		if (mRoute.getId() == 101l) {
			if ("University Direct 1".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString(UNIVERSITY, gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString(LAKERIDGE, gTrip.getDirectionId());
					return;
				}
			}
		}
		if (mRoute.getId() == 311l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		}
		if (mRoute.getId() == 314l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 331l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 332l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 335l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 338l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 339l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 342l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 348l) {
			if (gTrip.getDirectionId() == 0) {
				if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 352l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		} else if (mRoute.getId() == 358l) {
			if ("Special".equalsIgnoreCase(gTrip.getTripHeadsign())) {
				if (gTrip.getDirectionId() == 0) {
					mTrip.setHeadsignString("AM", gTrip.getDirectionId());
					return;
				} else if (gTrip.getDirectionId() == 1) {
					mTrip.setHeadsignString("PM", gTrip.getDirectionId());
					return;
				}
			}
		}
		mTrip.setHeadsignString(cleanTripHeadsign(gTrip.getTripHeadsign()), gTrip.getDirectionId());
	}

	@Override
	public boolean mergeHeadsign(MTrip mTrip, MTrip mTripToMerge) {
		List<String> headsignsValues = Arrays.asList(mTrip.getHeadsignValue(), mTripToMerge.getHeadsignValue());
		if (mTrip.getRouteId() == 4l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					MAYFAIR //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MAYFAIR, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CITY_CENTER, //
					WILLOWGROVE, //
					WILLOWGROVE_SQUARE //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(WILLOWGROVE_SQUARE, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 5l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					CONFEDERATION_TERMINAL //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
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
		} else if (mTrip.getRouteId() == 8l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					DOWNTOWN //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(DOWNTOWN, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 12l) {
			if (Arrays.asList( //
					AIRPORT, //
					CITY_CENTER //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(AIRPORT, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CITY_CENTER, //
					RIVER_HEIGHTS //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(RIVER_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 13l) {
			if (Arrays.asList( //
					BROADWAY, //
					UNIVERSITY //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(BROADWAY, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					UNIVERSITY, //
					LAWSON_HEIGHTS //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 19l) {
			if (Arrays.asList( //
					CENTRE_MALL, //
					MARKET_MALL //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CENTRE_MALL, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 22l) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					MONTGOMERY //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MONTGOMERY, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					UNIVERSITY //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 23l) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					HAMPTON_VILLAGE //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(HAMPTON_VILLAGE, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					BLAIRMORE, //
					CONFEDERATION_TERMINAL //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(BLAIRMORE, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 28l) {
			if (Arrays.asList( //
					FORESTGROVE, //
					SILVERSPRING //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(SILVERSPRING, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 30L) {
			if (Arrays.asList( //
					LAWSON_HEIGHTS, //
					LAWSON_TERMINAL, //
					CITY_CENTER //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 35l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					LAWSON_HEIGHTS, //
					LAWSON_TERMINAL //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 45l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					GARAGE //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (mTrip.getRouteId() == 60l) {
			if (Arrays.asList( //
					CITY_CENTER, //
					CONFEDERATION_TERMINAL //
					).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
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
