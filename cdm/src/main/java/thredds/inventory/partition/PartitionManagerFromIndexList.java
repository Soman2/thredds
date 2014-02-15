package thredds.inventory.partition;

import thredds.featurecollection.FeatureCollectionConfig;
import thredds.filesystem.MFileOS7;
import thredds.inventory.*;
import ucar.nc2.util.CloseableIterator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description
 *
 * @author John
 * @since 2/5/14                   `
 */
public class PartitionManagerFromIndexList extends CollectionAbstract implements PartitionManager {
  private List<File> partIndexFiles;
  private final FeatureCollectionConfig config;

  public PartitionManagerFromIndexList(MCollection dcm, List<File> partFiles, org.slf4j.Logger logger) {
    super(dcm.getCollectionName(), logger);
    this.config = (FeatureCollectionConfig) dcm.getAuxInfo(FeatureCollectionConfig.AUX_CONFIG);

    this.root = dcm.getRoot();
    this.partIndexFiles = partFiles;
  }

  public Iterable<MCollection> makePartitions(CollectionUpdateType forceCollection) throws IOException {
    return new PartIterator();
  }

  private class PartIterator implements Iterator<MCollection>, Iterable<MCollection> {
    Iterator<File> iter = partIndexFiles.iterator();

    @Override
    public Iterator<MCollection> iterator() {
      return this;
    }

    @Override
    public boolean hasNext() {
      return iter.hasNext();
    }

    @Override
    public MCollection next() {
      File nextFile = iter.next();

      try {
        MCollection result = new CollectionSingleIndexFile( new MFileOS7(nextFile.getPath()), logger);
        result.putAuxInfo(FeatureCollectionConfig.AUX_CONFIG, config);
        return result;

      } catch (IOException e) {
        logger.error("PartitionManagerFromList failed on "+nextFile.getPath(), e);
        throw new RuntimeException(e);
      }
    }

    @Override
    public void remove() {
    }
  }

  @Override
  public void close() {

  }

  @Override
  public Iterable<MFile> getFilesSorted() throws IOException {
    return null;
  }

  @Override
  public CloseableIterator<MFile> getFileIterator() throws IOException {
    return null;
  }
}
