package org.mtransit.parser.ca_saskatoon_transit_bus;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mtransit.commons.CleanUtils;
import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.MTLog;
import org.mtransit.parser.gtfs.data.GRoute;
import org.mtransit.parser.gtfs.data.GStop;
import org.mtransit.parser.gtfs.data.GTrip;
import org.mtransit.parser.mt.data.MAgency;
import org.mtransit.parser.mt.data.MTrip;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

// http://opendata-saskatoon.cloudapp.net/
// http://ww9.saskatoon.ca/app/qGoogleTransit/google_transit.zip
// http://apps2.saskatoon.ca/app/data/google_transit.zip
public class SaskatoonTransitBusAgencyTools extends DefaultAgencyTools {

	public static void main(@NotNull String[] args) {
		new SaskatoonTransitBusAgencyTools().start(args);
	}

	@Nullable
	@Override
	public List<Locale> getSupportedLanguages() {
		return LANG_EN;
	}

	@NotNull
	@Override
	public String getAgencyName() {
		return "Saskatoon Transit";
	}

	@Override
	public boolean defaultExcludeEnabled() {
		return true;
	}

	@Override
	public boolean excludeTrip(@NotNull GTrip gTrip) {
		if ("Not In Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return EXCLUDE;
		}
		if ("Out Of Service".equalsIgnoreCase(gTrip.getTripHeadsign())) {
			return EXCLUDE;
		}
		return super.excludeTrip(gTrip);
	}

	@NotNull
	@Override
	public String cleanServiceId(@NotNull String serviceId) {
		serviceId = CleanUtils.cleanMergedID(serviceId);
		return serviceId;
	}

	@NotNull
	@Override
	public Integer getAgencyRouteType() {
		return MAgency.ROUTE_TYPE_BUS;
	}

	@Override
	public boolean defaultRouteIdEnabled() {
		return true;
	}

	@Override
	public boolean useRouteShortNameForRouteId() {
		return false; // keep original route ID to match with GTFS Real-Time Alerts
	}

	@NotNull
	@Override
	public String cleanRouteShortName(@NotNull String routeShortName) {
		routeShortName = String.valueOf(Integer.parseInt(routeShortName.trim())); // remove leading '0'
		return routeShortName;
	}

	@Override
	public boolean defaultRouteLongNameEnabled() {
		return true;
	}

	@NotNull
	@Override
	public String cleanRouteLongName(@NotNull String routeLongName) {
		routeLongName = CleanUtils.toLowerCaseUpperCaseWords(getFirstLanguageNN(), routeLongName);
		return CleanUtils.cleanLabel(routeLongName);
	}

	@Override
	public boolean defaultAgencyColorEnabled() {
		return true;
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
	public String provideMissingRouteColor(@NotNull GRoute gRoute) {
		final int rsn = Integer.parseInt(gRoute.getRouteShortName().trim());
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

	@NotNull
	@Override
	public String cleanStopOriginalId(@NotNull String gStopId) {
		gStopId = CleanUtils.cleanMergedID(gStopId);
		return gStopId;
	}

	@Override
	public boolean directionFinderEnabled() {
		return true;
	}

	private static final Pattern FIX_FOREST_GROVE_ = Pattern.compile("((^|\\W)(forestgrove)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_FOREST_GROVE_REPLACEMENT = "$2" + "Forest Grove" + "$4";

	private static final Pattern FIX_CITY_ = Pattern.compile("((^|\\W)(ciity)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CITY_REPLACEMENT = "$2" + "City" + "$4";

	private static final Pattern FIX_CENTRE_ = Pattern.compile("((^|\\W)(cenrtre)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CENTRE_REPLACEMENT = "$2" + "Centre" + "$4";

	private static final Pattern FIX_CONFEDERATION_ = Pattern.compile("((^|\\W)(confederartion)(\\W|$))", Pattern.CASE_INSENSITIVE);
	private static final String FIX_CONFEDERATION_REPLACEMENT = "$2" + "Confederation" + "$4";

	@NotNull
	@Override
	public String cleanTripHeadsign(@NotNull String tripHeadsign) {
		tripHeadsign = FIX_CONFEDERATION_.matcher(tripHeadsign).replaceAll(FIX_CONFEDERATION_REPLACEMENT);
		tripHeadsign = FIX_FOREST_GROVE_.matcher(tripHeadsign).replaceAll(FIX_FOREST_GROVE_REPLACEMENT);
		tripHeadsign = FIX_CITY_.matcher(tripHeadsign).replaceAll(FIX_CITY_REPLACEMENT);
		tripHeadsign = FIX_CENTRE_.matcher(tripHeadsign).replaceAll(FIX_CENTRE_REPLACEMENT);
		tripHeadsign = CleanUtils.keepToAndRemoveVia(tripHeadsign);
		tripHeadsign = CleanUtils.cleanBounds(tripHeadsign);
		tripHeadsign = CleanUtils.cleanStreetTypes(tripHeadsign);
		return CleanUtils.cleanLabel(tripHeadsign);
	}

	@NotNull
	@Override
	public String cleanStopName(@NotNull String gStopName) {
		gStopName = CleanUtils.cleanBounds(gStopName);
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
