/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.util;

import java.io.File;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2019/6/25
 */
public final class PlatformDependent {
	private static final Pattern BIT_PATTERN = Pattern.compile("([1-9][0-9]+)-?bit");

	public static boolean isWindows() {
		return SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
	}

	public static boolean isAndroid() {
		// Idea: Sometimes java binaries include Android classes on the classpath,
		// even if it isn't actually Android.
		// Rather than check if certain classes are present, just check the VM, which is tied to the JDK.

		// Optional improvement: check if `android.os.Build.VERSION` is >= 24. On later versions of Android, the
		// OpenJDK is used, which means `Unsafe` will actually work as expected.

		// Android sets this property to Dalvik, regardless of whether it actually is.
		String vmName = SystemPropertyUtil.get("java.vm.name");
		return "Dalvik".equals(vmName);
	}

	public static boolean isOsx() {
		String osname = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US)
			.replaceAll("[^a-z0-9]+", "");
		return osname.startsWith("macosx") || osname.startsWith("osx");
	}

	public static boolean isJ9Jvm() {
		String vmName = SystemPropertyUtil.get("java.vm.name", "").toLowerCase();
		return vmName.startsWith("ibm j9") || vmName.startsWith("eclipse openj9");
	}

	public static boolean isIkvmDotNet() {
		String vmName = SystemPropertyUtil.get("java.vm.name", "").toUpperCase(Locale.US);
		return "IKVM.NET".equals(vmName);
	}

	public static File tmpdir() {
		File f;
		try {
			f = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
			if (f != null) {
				return f;
			}

			f = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
			if (f != null) {
				return f;
			}

			// This shouldn't happen, but just in case ..
			if (isWindows()) {
				f = toDirectory(System.getenv("TEMP"));
				if (f != null) {
					return f;
				}

				String userprofile = System.getenv("USERPROFILE");
				if (userprofile != null) {
					f = toDirectory(userprofile + "\\AppData\\Local\\Temp");
					if (f != null) {
						return f;
					}

					f = toDirectory(userprofile + "\\Local Settings\\Temp");
					if (f != null) {
						return f;
					}
				}
			} else {
				f = toDirectory(System.getenv("TMPDIR"));
				if (f != null) {
					return f;
				}
			}
		} catch (Throwable ignored) {
			// Environment variable inaccessible
		}

		// Last resort.
		if (isWindows()) {
			f = new File("C:\\Windows\\Temp");
		} else {
			f = new File("/tmp");
		}

		return f;
	}

	private static File toDirectory(String path) {
		if (path == null) {
			return null;
		}

		File f = new File(path);
		f.mkdirs();

		if (!f.isDirectory()) {
			return null;
		}

		try {
			return f.getAbsoluteFile();
		} catch (Exception ignored) {
			return f;
		}
	}

	private static int bitMode0() {
		// Check user-specified bit mode first.
		int bitMode = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
		if (bitMode > 0) {
			return bitMode;
		}

		// And then the vendor specific ones which is probably most reliable.
		bitMode = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
		if (bitMode > 0) {
			return bitMode;
		}
		bitMode = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
		if (bitMode > 0) {
			return bitMode;
		}

		// os.arch also gives us a good hint.
		String arch = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
		if ("amd64".equals(arch) || "x86_64".equals(arch)) {
			bitMode = 64;
		} else if ("i386".equals(arch) || "i486".equals(arch) || "i586".equals(arch) || "i686".equals(arch)) {
			bitMode = 32;
		}

		// Last resort: guess from VM name and then fall back to most common 64-bit mode.
		String vm = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
		Matcher m = BIT_PATTERN.matcher(vm);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		} else {
			return 64;
		}
	}
}
