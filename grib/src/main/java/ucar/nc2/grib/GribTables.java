/*
 * Copyright (c) 1998-2018 John Caron and University Corporation for Atmospheric Research/Unidata
 * See LICENSE for license information.
 */

package ucar.nc2.grib;

import javax.annotation.Nullable;

/**
 * Abstraction of GribTable for Grib Collections.
 * Still figuring out what the minimal need is
 *
 * @author John
 * @since 9/5/11
 */
public interface GribTables {

  @Nullable
  String getSubCenterName(int center, int subcenter);

  String getLevelNameShort(int code);

  @Nullable
  GribStatType getStatType(int intvType);

  VertCoord.VertUnit getVertUnit(int code);

  @Nullable
  String getGeneratingProcessName(int code);

  @Nullable
  String getGeneratingProcessTypeName(int code);

  interface Parameter {
    int getDiscipline();

    int getCategory();

    int getNumber();

    int getValue();

    String getName();

    String getUnit();

    @Nullable
    String getAbbrev();

    String getDescription();

    String getId();

    @Nullable
    Float getFill();

    Float getMissing();
  }


}
