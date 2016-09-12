package resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resourcer {
	private static final String DEFAULT_PROPERTY_NAME = "resources.text";
	private static String basename = null;
	private static ResourceBundle resources = null;

	private Resourcer() {
	}

	public static void createResourcer() {
		Resourcer.createResourcer(null);
	}

	public static void setBasename(String basename) {
		if (basename == null) {
			if (Resourcer.basename == null) {
				Resourcer.basename = Resourcer.DEFAULT_PROPERTY_NAME;
			}
		} else {
			Resourcer.basename = basename;
		}
	}

	public static void createResourcer(String basename) {
		if (Resourcer.resources == null) {
			Resourcer.setBasename(basename);
			Resourcer.resources = ResourceBundle.getBundle(Resourcer.basename,
					Locale.getDefault());
		} else {
			Locale systemLocale = Locale.getDefault();
			Locale resourcerLocale = Resourcer.resources.getLocale();
			if (!(resourcerLocale.equals(systemLocale))) {
				Resourcer.resources = ResourceBundle.getBundle(
						Resourcer.basename, systemLocale);
			}
		}
	}

	public static String getString(String parameter) {
		Resourcer.createResourcer();
		return Resourcer.resources.getString(parameter);
	}
}
