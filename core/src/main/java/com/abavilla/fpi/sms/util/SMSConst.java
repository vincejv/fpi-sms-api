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

/**
 * Constants used for SMS API Service.
 */
public abstract class SMSConst {

  /**
   * Region Code for the Philippines used for phone number parsing
   */
  public static final String PH_REGION_CODE = "PH";

  /**
   * Code to return if SMS was delivered successfully
   */
  public static final int SMS_SENT_SUCCESS = 0;

  /**
   * Code to return if SMS delivery encountered an error with backend SMS API
   */
  public static final int SMS_API_FAIL = 1;

  /**
   * Code to return if SMS was delivery encountered an error while saving the log in SMS DB, SMS may or may not be
   * delivered to recipient
   */
  public static final int SMS_MONGO_FAIL = 2;

  /**
   * Code to return if SMS was delivery encountered an error during parsing the phone number
   */
  public static final int SMS_MOBILE_FORMAT_ERR = 3;

  /**
   * Code to return if SMS was not sent as user opt-out from the service
   */
  public static final int SMS_USER_OPT_OUT = 4;

  private SMSConst() {
    // constants class
  }

}
