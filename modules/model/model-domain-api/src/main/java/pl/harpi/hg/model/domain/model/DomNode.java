package pl.harpi.hg.model.domain.model;

import lombok.val;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import java.util.List;

public class DomNode extends Xpp3Dom {
  public DomNode(String name, String value, List<DomNode> children) {
    super(name);
    setValue(value);

    if (children != null) {
      children.forEach(this::addChild);
    }
  }

  public DomNode(String name, String value) {
    this(name, value, null);
  }
}
