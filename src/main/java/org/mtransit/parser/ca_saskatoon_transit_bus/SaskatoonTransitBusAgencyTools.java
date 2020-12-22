package org.mtransit.parser.ca_saskatoon_transit_bus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mtransit.parser.CleanUtils;
import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.MTLog;
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
import org.mtransit.parser.mt.data.MRoute;
import org.mtransit.parser.mt.data.MTrip;
import org.mtransit.parser.mt.data.MTripStop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

// http://opendata-saskatoon.cloudapp.net/
// http://ww9.saskatoon.ca/app/qGoogleTransit/google_transit.zip
// http://apps2.saskatoon.ca/app/data/google_transit.zip
public class SaskatoonTransitBusAgencyTools extends DefaultAgencyTools {

	public static void main(@Nullable String[] args) {
		if (args == null || args.length == 0) {
			args = new String[3];
			args[0] = "input/gtfs.zip";
			args[1] = "../../mtransitapps/ca-saskatoon-transit-bus-android/res/raw/";
			args[2] = ""; // files-prefix
		}
		new SaskatoonTransitBusAgencyTools().start(args);
	}

	@Nullable
	private HashSet<Integer> serviceIdInts;

	@Override
	public void start(@NotNull String[] args) {
		MTLog.log("Generating Saskatoon Transit bus data...");
		long start = System.currentTimeMillis();
		this.serviceIdInts = extractUsefulServiceIdInts(args, this, true);
		super.start(args);
		MTLog.log("Generating Saskatoon Transit bus data... DONE in %s.", Utils.getPrettyDuration(System.currentTimeMillis() - start));
	}

	@Override
	public boolean excludingAll() {
		return this.serviceIdInts != null && this.serviceIdInts.isEmpty();
	}

	@Override
	public boolean excludeCalendar(@NotNull GCalendar gCalendar) {
		if (this.serviceIdInts != null) {
			return excludeUselessCalendarInt(gCalendar, this.serviceIdInts);
		}
		return super.excludeCalendar(gCalendar);
	}

	@Override
	public boolean excludeCalendarDate(@NotNull GCalendarDate gCalendarDates) {
		if (this.serviceIdInts != null) {
			return excludeUselessCalendarDateInt(gCalendarDates, this.serviceIdInts);
		}
		return super.excludeCalendarDate(gCalendarDates);
	}

	@Override
	public boolean excludeTrip(@NotNull GTrip gTrip) {
		if ("Not In Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return true;
		}
		if ("Out Of Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return true;
		}
		if (this.serviceIdInts != null) {
			return excludeUselessTripInt(gTrip, this.serviceIdInts);
		}
		return super.excludeTrip(gTrip);
	}

	@NotNull
	@Override
	public Integer getAgencyRouteType() {
		return MAgency.ROUTE_TYPE_BUS;
	}

	private final HashMap<Long, Long> routeIdsToShortName = new HashMap<>();

	@Override
	public long getRouteId(@NotNull GRoute gRoute) {
		routeIdsToShortName.put(super.getRouteId(gRoute), Long.parseLong(gRoute.getRouteShortName().trim()));
		return super.getRouteId(gRoute); // keep original route ID to match with GTFS Real-Time Alerts
	}

	@Nullable
	@Override
	public String getRouteShortName(@NotNull GRoute gRoute) {
		return String.valueOf(Integer.parseInt(gRoute.getRouteShortName().trim())); // remove leading '0'
	}

	@NotNull
	@Override
	public String getRouteLongName(@NotNull GRoute gRoute) {
		return cleanRouteLongName(gRoute.getRouteLongName());
	}

	private String cleanRouteLongName(String routeLongName) {
		if (Utils.isUppercaseOnly(routeLongName, true, true)) {
			routeLongName = routeLongName.toLowerCase(Locale.ENGLISH);
		}
		return CleanUtils.cleanLabel(routeLongName);
	}

	private static final String AGENCY_COLOR_BLUE = "027AA7"; // BLUE (from web site CSS)

	private static final String AGENCY_COLOR = AGENCY_COLOR_BLUE;

	@NotNull
	@Override
	public String getAgencyColor() {
		return AGENCY_COLOR;
	}

	private static final String COLOR_DART_GREEN = "8CC63F"; // GREEN (from system map PDF)
	private static final String SCHOOL_BUS_COLOR = "FFD800"; // YELLOW (from Wikipedia)

	@SuppressWarnings("DuplicateBranchesInSwitch")
	@Nullable
	@Override
	public String getRouteColor(@NotNull GRoute gRoute) {
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
		case 27: return null;
		case 28: return null;
		case 29: return null;
		case 30: return null;
		case 35: return null;
		case 40: return null;
		case 44: return null;
		case 43: return null;
		case 45: return null;
		case 50: return COLOR_DART_GREEN;
		case 55: return null;
		case 60: return COLOR_DART_GREEN;
		case 61: return null;
		case 62: return null;
		case 63: return null;
		case 64: return null;
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
		//
		case 101: return SCHOOL_BUS_COLOR;
		case 102: return SCHOOL_BUS_COLOR;
		case 104: return SCHOOL_BUS_COLOR;
		case 117: return SCHOOL_BUS_COLOR;
		case 150: return SCHOOL_BUS_COLOR;
		case 151: return SCHOOL_BUS_COLOR;
		case 152: return SCHOOL_BUS_COLOR;
		case 153: return SCHOOL_BUS_COLOR;
		case 180: return SCHOOL_BUS_COLOR;
		//
		case 305: return SCHOOL_BUS_COLOR;
		case 311: return SCHOOL_BUS_COLOR;
		case 314: return SCHOOL_BUS_COLOR;
		case 315: return SCHOOL_BUS_COLOR;
		case 316: return SCHOOL_BUS_COLOR;
		case 322: return SCHOOL_BUS_COLOR;
		case 325: return SCHOOL_BUS_COLOR;
		case 331: return SCHOOL_BUS_COLOR;
		case 332: return SCHOOL_BUS_COLOR;
		case 333: return SCHOOL_BUS_COLOR;
		case 334: return SCHOOL_BUS_COLOR;
		case 335: return SCHOOL_BUS_COLOR;
		case 336: return SCHOOL_BUS_COLOR;
		case 338: return SCHOOL_BUS_COLOR;
		case 339: return SCHOOL_BUS_COLOR;
		case 341: return SCHOOL_BUS_COLOR;
		case 342: return SCHOOL_BUS_COLOR;
		case 346: return SCHOOL_BUS_COLOR;
		case 348: return SCHOOL_BUS_COLOR;
		case 349: return SCHOOL_BUS_COLOR;
		case 352: return SCHOOL_BUS_COLOR;
		case 354: return SCHOOL_BUS_COLOR;
		case 356: return SCHOOL_BUS_COLOR;
		case 358: return SCHOOL_BUS_COLOR;
		//
		case 514: return null;
		case 523: return null;
		//
		case 808: return null;
		//
		case 1225: return null;
		// @formatter:on
		default:
			throw new MTLog.Fatal("Unexpected route color %s!", gRoute.toStringPlus());
		}
	}

	private static final String MAYFAIR = "Mayfair";
	private static final String WILLOWGROVE = "Willowgrove";
	private static final String WILLOWGROVE_SQUARE = WILLOWGROVE + " Sq";
	private static final String MC_CORMACK = "McCormack";
	private static final String RIVER_HEIGHTS = "River Hts";
	private static final String AIRPORT = "Airport";
	private static final String LAWSON = "Lawson";
	private static final String LAWSON_HEIGHTS = LAWSON + " Hts";
	private static final String LAWSON_HEIGHTS_TERMINAL = LAWSON_HEIGHTS + " Terminal";
	private static final String LAWSON_TERMINAL = LAWSON + " Terminal";
	private static final String BROADWAY = "Broadway";
	private static final String STONEBRIDGE = "Stonebridge";
	private static final String MARKET_MALL = "Mkt Mall";
	private static final String CENTRE_MALL = "Ctr Mall";
	private static final String MONTGOMERY = "Montgomery";
	private static final String BLAIRMORE = "Blairmore";
	private static final String UNIVERSITY = "University";
	private static final String CONFEDERATION = "Confederation";
	private static final String CONFEDERATION_TERMINAL = CONFEDERATION + " Terminal";
	private static final String HAMPTON_VILLAGE = "Hampton Vlg";
	private static final String DOWNTOWN = "Downtown";
	private static final String CITY = "City";
	private static final String CITY_CENTER = CITY + " Ctr";
	private static final String FOREST_GROVE = "Forest Grv";
	private static final String SILVERSPRING = "Silverspring";
	private static final String GARAGE = "Garage";
	private static final String KENSINGTON = "Kensington";
	private static final String CIVIC_OPERATIONS_CENTER = "Civic Operations Ctr";
	private static final String NORTH_INDUSTRIAL = "North Ind";
	private static final String SASK_TEL_CENTER = "Sask Tel Ctr";

	private static final HashMap<Long, RouteTripSpec> ALL_ROUTE_TRIPS2;

	static {
		HashMap<Long, RouteTripSpec> map2 = new HashMap<>();
		//noinspection deprecation
		map2.put(11540L, new RouteTripSpec(11540L, // 25
				0, MTrip.HEADSIGN_TYPE_STRING, SASK_TEL_CENTER, //
				1, MTrip.HEADSIGN_TYPE_STRING, NORTH_INDUSTRIAL) //
				.addTripSort(0, //
						Arrays.asList( //
								"3459", // Millar / 60th Street #NorthInd
								"3346", // ++
								"5588" // Apex / Bill Hunter #SaskTelCtr
						)) //
				.addTripSort(1, //
						Arrays.asList( //
								"5588", // Apex / Bill Hunter #SaskTelCtr
								"5573", // ==
								"3804", // !=
								"4381", // !=
								"4440", // ==
								"3459" // Millar / 60th Street #NorthInd
						)) //
				.compileBothTripSort());
		//noinspection deprecation
		map2.put(11410L, new RouteTripSpec(11410L, // 25
				0, MTrip.HEADSIGN_TYPE_STRING, SASK_TEL_CENTER, //
				1, MTrip.HEADSIGN_TYPE_STRING, NORTH_INDUSTRIAL) //
				.addTripSort(0, //
						Arrays.asList( //
								"3459", // Millar / 60th Street #NorthInd
								"3346", // ++
								"5588" // Apex / Bill Hunter #SaskTelCtr
						)) //
				.addTripSort(1, //
						Arrays.asList( //
								"5588", // Apex / Bill Hunter #SaskTelCtr
								"5573", // ==
								"3804", // !=
								"4381", // !=
								"4440", // ==
								"3459" // Millar / 60th Street #NorthInd
						)) //
				.compileBothTripSort());
		ALL_ROUTE_TRIPS2 = map2;
	}

	@NotNull
	@Override
	public String cleanStopOriginalId(@NotNull String gStopId) {
		gStopId = CleanUtils.cleanMergedID(gStopId);
		return gStopId;
	}

	@Override
	public int compareEarly(long routeId, @NotNull List<MTripStop> list1, @NotNull List<MTripStop> list2, @NotNull MTripStop ts1, @NotNull MTripStop ts2, @NotNull GStop ts1GStop, @NotNull GStop ts2GStop) {
		if (ALL_ROUTE_TRIPS2.containsKey(routeId)) {
			return ALL_ROUTE_TRIPS2.get(routeId).compare(routeId, list1, list2, ts1, ts2, ts1GStop, ts2GStop, this);
		}
		return super.compareEarly(routeId, list1, list2, ts1, ts2, ts1GStop, ts2GStop);
	}

	@NotNull
	@Override
	public ArrayList<MTrip> splitTrip(@NotNull MRoute mRoute, @Nullable GTrip gTrip, @NotNull GSpec gtfs) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return ALL_ROUTE_TRIPS2.get(mRoute.getId()).getAllTrips();
		}
		return super.splitTrip(mRoute, gTrip, gtfs);
	}

	@NotNull
	@Override
	public Pair<Long[], Integer[]> splitTripStop(@NotNull MRoute mRoute, @NotNull GTrip gTrip, @NotNull GTripStop gTripStop, @NotNull ArrayList<MTrip> splitTrips, @NotNull GSpec routeGTFS) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return SplitUtils.splitTripStop(mRoute, gTrip, gTripStop, routeGTFS, ALL_ROUTE_TRIPS2.get(mRoute.getId()), this);
		}
		return super.splitTripStop(mRoute, gTrip, gTripStop, splitTrips, routeGTFS);
	}

	@Override
	public void setTripHeadsign(@NotNull MRoute mRoute, @NotNull MTrip mTrip, @NotNull GTrip gTrip, @NotNull GSpec gtfs) {
		if (ALL_ROUTE_TRIPS2.containsKey(mRoute.getId())) {
			return; // split
		}
		long rsn = routeIdsToShortName.get(mTrip.getRouteId());
		if (rsn == 8L) {
			if (gTrip.getTripHeadsignOrDefault().trim().isEmpty()) {
				if (mTrip.getRouteId() == 11325L && gTrip.getDirectionIdOrDefault() == 1) {
					mTrip.setHeadsignString("City Ctr", gTrip.getDirectionIdOrDefault());
					return;
				}
			}
		}
		mTrip.setHeadsignString(
				cleanTripHeadsign(gTrip.getTripHeadsignOrDefault()),
				gTrip.getDirectionIdOrDefault()
		);
	}

	@Override
	public boolean mergeHeadsign(@NotNull MTrip mTrip, @NotNull MTrip mTripToMerge) {
		if (MTrip.mergeEmpty(mTrip, mTripToMerge)) {
			return true;
		}
		List<String> headsignsValues = Arrays.asList(mTrip.getHeadsignValue(), mTripToMerge.getHeadsignValue());
		long rsn = routeIdsToShortName.get(mTrip.getRouteId());
		if (rsn == 4L) {
			if (Arrays.asList( //
					UNIVERSITY, //
					CITY_CENTER // ==
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					UNIVERSITY, //
					CITY_CENTER, // ==
					MAYFAIR //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MAYFAIR, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CITY_CENTER, // ==
					WILLOWGROVE, //
					WILLOWGROVE_SQUARE //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(WILLOWGROVE_SQUARE, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 5L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // <>
					CITY_CENTER // <>
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CITY_CENTER, // <>
					CONFEDERATION_TERMINAL, // <>
					CIVIC_OPERATIONS_CENTER, //
					AIRPORT, //
					"Meadowgreen", //
					MC_CORMACK //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MC_CORMACK, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 6L) {
			if (mTrip.getHeadsignId() == 0) {
				if (Arrays.asList( //
						MARKET_MALL, // ==
						BROADWAY, // ==
						UNIVERSITY, // !=
						CITY_CENTER // ==
				).containsAll(headsignsValues)) {
					mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
					return true;
				}
			} else if (mTrip.getHeadsignId() == 1) {
				if (Arrays.asList( //
						CITY_CENTER, // ==
						BROADWAY, // ==
						MARKET_MALL // ==
				).containsAll(headsignsValues)) {
					mTrip.setHeadsignString(MARKET_MALL, mTrip.getHeadsignId());
					return true;
				}
			}
		} else if (rsn == 8L) {
			if (Arrays.asList( //
					CITY_CENTER, //
					DOWNTOWN //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(DOWNTOWN, mTrip.getHeadsignId()); // CITY_CENTER
				return true;
			} else if (Arrays.asList( //
					"Briarwood", //
					"8th St", //
					CENTRE_MALL //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CENTRE_MALL, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 9L) {
			if (Arrays.asList( //
					"Riversdale", // <>
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 10L) {
			if (Arrays.asList( //
					CIVIC_OPERATIONS_CENTER, //
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 12L) {
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
		} else if (rsn == 13L) {
			if (Arrays.asList( //
					UNIVERSITY, // <>
					BROADWAY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(BROADWAY, mTrip.getHeadsignId());
				return true;
			}
			if (Arrays.asList( //
					UNIVERSITY, // <>
					LAWSON_TERMINAL, //
					LAWSON_HEIGHTS //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
			if (Arrays.asList( //
					UNIVERSITY, // <>
					LAWSON_HEIGHTS_TERMINAL, //
					LAWSON_HEIGHTS //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 14L) {
			if (Arrays.asList( //
					"Exhibition", //
					NORTH_INDUSTRIAL).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(NORTH_INDUSTRIAL, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 19L) {
			if (Arrays.asList( //
					CENTRE_MALL, //
					MARKET_MALL //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CENTRE_MALL, mTrip.getHeadsignId());
				return true;
			}
			if (Arrays.asList( //
					"Cross/Murray/Bowman", //
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 22L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // SAME
					MONTGOMERY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MONTGOMERY, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // SAME
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // SAME
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 23L) {
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
		} else if (rsn == 26L) {
			if (Arrays.asList( //
					FOREST_GROVE, // ==
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 27L) {
			if (Arrays.asList( //
					SILVERSPRING, //
					"Evergreen" //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString("Evergreen", mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 28L) {
			if (Arrays.asList( //
					FOREST_GROVE, //
					SILVERSPRING //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(SILVERSPRING, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 30L) {
			if (Arrays.asList( //
					CITY_CENTER, // <>
					LAWSON_HEIGHTS //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(LAWSON_HEIGHTS, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 35L) {
			if (Arrays.asList( //
					CITY_CENTER, //
					LAWSON_HEIGHTS, //
					LAWSON_TERMINAL //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 40L) {
			if (Arrays.asList( //
					UNIVERSITY, //
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 44L) {
			if (Arrays.asList( //
					UNIVERSITY, //
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 45L) {
			if (Arrays.asList( //
					CITY_CENTER, //
					GARAGE, //
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 60L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					CONFEDERATION //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CONFEDERATION, mTrip.getHeadsignId());
				return true;
			}
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					CITY_CENTER, //
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 61L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					HAMPTON_VILLAGE, //
					CITY_CENTER, //
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 62L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // <>
					CITY_CENTER, //
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			} else if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // <>
					MONTGOMERY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(MONTGOMERY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 63L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					HAMPTON_VILLAGE //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(HAMPTON_VILLAGE, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 64L) {
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, //
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 65L) {
			if (Arrays.asList( //
					CONFEDERATION, //
					CONFEDERATION_TERMINAL, // <>
					KENSINGTON //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(KENSINGTON, mTrip.getHeadsignId());
				return true;
			}
			if (Arrays.asList( //
					CONFEDERATION_TERMINAL, // <>
					CITY_CENTER //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(CITY_CENTER, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 81L) {
			if (Arrays.asList( //
					CENTRE_MALL, // <>
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 82L) {
			if (Arrays.asList( //
					CENTRE_MALL, // <>
					UNIVERSITY //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(UNIVERSITY, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 83L) {
			if (Arrays.asList( //
					CENTRE_MALL, // <>
					STONEBRIDGE //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString(STONEBRIDGE, mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 84L) {
			if (Arrays.asList( //
					CENTRE_MALL, // <>
					"Briarwood" //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString("Briarwood", mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 86L) {
			if (Arrays.asList( //
					CENTRE_MALL, // <>
					"Rosewood" //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString("Rosewood", mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 151L) {
			if (Arrays.asList( //
					"Folkfest Prairieland", // <>
					"Folkfest East" //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString("Folkfest East", mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 336L) {
			if (Arrays.asList( //
					"Ctr Mall", //
					"Ctr Mall Terminal" //
			).containsAll(headsignsValues)) {
				mTrip.setHeadsignString("Ctr Mall Terminal", mTrip.getHeadsignId());
				return true;
			}
		} else if (rsn == 1225L) {
			if (mTrip.getHeadsignId() == 0) {
				if (Arrays.asList( //
						"JBE Mkt Mall", // <>
						"JBE Downtown", // <>
						"JBE Ctr Mall", // <>
						"JBE - Ctr Mall", // <>
						"JBE- Mkt Mall", // <>
						"CCW" // ++
				).containsAll(headsignsValues)) {
					mTrip.setHeadsignString("CCW", mTrip.getHeadsignId());
					return true;
				}
			} else if (mTrip.getHeadsignId() == 1) {
				if (Arrays.asList( //
						"JBE Mkt Mall", // <>
						"JBE Downtown", // <>
						"JBE Ctr Mall", // <>
						"JBE - Ctr Mall", // <>
						"JBE- Mkt Mall", // <>
						"JBE - Mkt Mall", //
						"Ctr Mall", //
						"CW" // ++
				).containsAll(headsignsValues)) {
					mTrip.setHeadsignString("CW", mTrip.getHeadsignId());
					return true;
				}
			}
		}
		throw new MTLog.Fatal("%s: Unexpected trips to merge %s and %s.", rsn, mTrip, mTripToMerge);
	}

	private static final Pattern INDUSTRIAL_ = Pattern.compile("((^|\\W)(industrial)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String INDUSTRIAL_REPLACEMENT = "$2" + "Ind" + "$4";

	private static final Pattern FIX_FOREST_GROVE_ = Pattern.compile("((^|\\W)(forestgrove)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_FOREST_GROVE_REPLACEMENT = "$2" + FOREST_GROVE + "$4";

	private static final Pattern FIX_CITY_ = Pattern.compile("((^|\\W)(ciity)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CITY_REPLACEMENT = "$2" + CITY + "$4";

	private static final Pattern FIX_CENTRE_ = Pattern.compile("((^|\\W)(cenrtre)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CENTRE_REPLACEMENT = "$2" + "Centre" + "$4";

	private static final Pattern FIX_CONFEDERATION_ = Pattern.compile("((^|\\W)(confederartion)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CONFEDERATION_REPLACEMENT = "$2" + CONFEDERATION + "$4";

	@NotNull
	@Override
	public String cleanTripHeadsign(@NotNull String tripHeadsign) {
		tripHeadsign = INDUSTRIAL_.matcher(tripHeadsign).replaceAll(INDUSTRIAL_REPLACEMENT);
		tripHeadsign = FIX_CONFEDERATION_.matcher(tripHeadsign).replaceAll(FIX_CONFEDERATION_REPLACEMENT);
		tripHeadsign = FIX_FOREST_GROVE_.matcher(tripHeadsign).replaceAll(FIX_FOREST_GROVE_REPLACEMENT);
		tripHeadsign = FIX_CITY_.matcher(tripHeadsign).replaceAll(FIX_CITY_REPLACEMENT);
		tripHeadsign = FIX_CENTRE_.matcher(tripHeadsign).replaceAll(FIX_CENTRE_REPLACEMENT);
		tripHeadsign = CleanUtils.keepToAndRemoveVia(tripHeadsign);
		tripHeadsign = CleanUtils.cleanStreetTypes(tripHeadsign);
		return CleanUtils.cleanLabel(tripHeadsign);
	}

	@NotNull
	@Override
	public String cleanStopName(@NotNull String gStopName) {
		gStopName = CleanUtils.cleanStreetTypes(gStopName);
		gStopName = CleanUtils.cleanNumbers(gStopName);
		return CleanUtils.cleanLabel(gStopName);
	}

	@Override
	public int getStopId(@NotNull GStop gStop) {
		try {
			return Integer.parseInt(gStop.getStopCode()); // use stop code as stop ID
		} catch (Exception e) {
			throw new MTLog.Fatal(e, "Error while extracting stop ID from " + gStop);
		}
	}
}
