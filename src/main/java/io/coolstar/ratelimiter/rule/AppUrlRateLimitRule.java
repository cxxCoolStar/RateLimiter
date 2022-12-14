package io.coolstar.ratelimiter.rule;

import io.coolstar.ratelimiter.exception.InvalidUrlException;
import io.coolstar.ratelimiter.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Trie tree to store {@link ApiLimit} for one app. This class is thread-safe.
 */
public class AppUrlRateLimitRule {

  private static final Logger logger = LoggerFactory.getLogger(AppUrlRateLimitRule.class);

  private Node root;

  public AppUrlRateLimitRule() {
    root = new Node("/");
  }

  /**
   * add limit info
   *
   * 1. 计算
   *
   * @param apiLimit
   * @throws InvalidUrlException
   */
  public void addLimitInfo(ApiLimit apiLimit) throws InvalidUrlException {
    //1.
  }

  /**
   *  add limit infos
   *  TODO(chenxingxing)
   *
   * @param apiLimits
   * @throws InvalidUrlException
   */
  public void addLimitInfos(Collection<ApiLimit> apiLimits) throws InvalidUrlException {


  }

  /**
   * Get limit info for the url path.
   * 
   * TODO(chenxingxing): validate urlPath first.
   * TODO(chenxingxing): handle the case: children has multiply matched dir patterns.
   * TODO(chenxingxing): cache Regex Pattern for performance
   * 
   * @param urlPath urlPath path
   * @return the limit info for the urlPath.
   * @throws InvalidUrlException if the url path is invalid.
   */
  public ApiLimit getLimitInfo(String urlPath) throws InvalidUrlException {

    return new ApiLimit();
  }

  public static final class Node {

    private String pathDir;

    private boolean isPattern;

    private ConcurrentHashMap<String, Node> edges = new ConcurrentHashMap<>();

    private ApiLimit apiLimit;

    public Node() {}

    public Node(String pathDir) {
      this(pathDir, false);
    }

    public Node(String pathDir, boolean isPattern) {
      this.pathDir = pathDir;
      this.isPattern = isPattern;
    }

    public void setIsPattern(boolean isPattern) {
      this.isPattern = isPattern;
    }

    public boolean getIsPattern() {
      return isPattern;
    }

    public String getPathDir() {
      return pathDir;
    }

    public void setPathDir(String pathDir) {
      this.pathDir = pathDir;
    }

    public ConcurrentHashMap<String, Node> getEdges() {
      return edges;
    }

    public ApiLimit getApiLimit() {
      return apiLimit;
    }

    public void setApiLimit(ApiLimit apiLimit) {
      this.apiLimit = apiLimit;
    }
  }

  private boolean isUrlTemplateVariable(String pathDir) {
    return pathDir.startsWith("{") && pathDir.endsWith("}");
  }

  private String getPathDirPatten(String pathDir) {
    StringBuilder patternBuilder = new StringBuilder();
    int colonIdx = pathDir.indexOf(':');
    if (colonIdx == -1) {
      patternBuilder.append("(^[0-9]*$)"); // default url template variable pattern: ID
    } else {
      String variablePattern = pathDir.substring(colonIdx + 1, pathDir.length() - 1);
      patternBuilder.append('(');
      patternBuilder.append(variablePattern);
      patternBuilder.append(')');
    }
    return patternBuilder.toString();
  }

}
