/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

package com.abavilla.fpi.sms.util;

import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SMSUtil {
  private static final char[] BASIC_CHARS = {
      // Basic Character Set
      '@', '£', '$', '¥', 'è', 'é', 'ù', 'ì', 'ò', 'Ç', '\n', 'Ø', 'ø', '\r', 'Å', 'å',
      'Δ', '_', 'Φ', 'Γ', 'Λ', 'Ω', 'Π', 'Ψ', 'Σ', 'Θ', 'Ξ', 'Æ', 'æ', 'ß', 'É',
      ' ', '!', '"', '#', '¤', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
      '¡', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
      'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Ä', 'Ö', 'Ñ', 'Ü', '§',
      '¿', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
      'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ä', 'ö', 'ñ', 'ü', 'à',
      // Basic Character Set Extension
      '\f', '^', '{', '}', '\\', '[', '~', ']', '|', '€'
  };

  private static final boolean[] ENCODEABLE_BY_ORD_UP_TO_253 = new boolean[254];
  private static final Set<Character> ENCODEABLE_REST = new HashSet<>();
  static {
    for (char ch : BASIC_CHARS) {
      if (ch <= 253) {
        ENCODEABLE_BY_ORD_UP_TO_253[ch] = true;
      } else {
        ENCODEABLE_REST.add(ch);
      }
    }
  }

  public static boolean isEncodeableInGsm0338(String msg) {
    final int length = msg.length();
    for (int i = 0; i < length; ++i) {
      char ch = msg.charAt(i);
      if (ch <= 253) {
        if (!ENCODEABLE_BY_ORD_UP_TO_253[ch]) {
          return false;
        }
      } else {
        if (!ENCODEABLE_REST.contains(ch)) {
          return false;
        }
      }
    }
    return true;
  }
}
