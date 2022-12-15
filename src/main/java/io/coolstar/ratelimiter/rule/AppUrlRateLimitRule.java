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
     * <p>
     * 1. 计算
     *
     * @param apiLimit
     * @throws InvalidUrlException
     */
    public void addLimitInfo(ApiLimit apiLimit) throws InvalidUrlException {
        String api = apiLimit.getApi();
        List<String> pathDirs = UrlUtils.tokenizeUrlPath(api);
        Node cur = root;
        for (int i = 0; i < pathDirs.size(); i++) {
            ConcurrentHashMap<String, Node> edges = cur.edges;
            Node node = new Node(pathDirs.get(i));
            // 如果为最后一个元素,设置限流
            if (i == (pathDirs.size() - 1)) {
                node.setApiLimit(apiLimit);
            }
            edges.putIfAbsent(pathDirs.get(i),node);
            cur = edges.get(pathDirs.get(i));
        }
    }

    /**
     * add limit infos
     *
     * @param apiLimits
     * @throws InvalidUrlException
     */
    public void addLimitInfos(Collection<ApiLimit> apiLimits) throws InvalidUrlException {

        for (ApiLimit apiLimit : apiLimits) {
            addLimitInfo(apiLimit);
        }
    }

    /**
     * Get limit info for the url path.
     * <p>
     * TODO(chenxingxing): cache Regex Pattern for performance
     *
     * @param urlPath urlPath path
     * @return the limit info for the urlPath.
     * @throws InvalidUrlException if the url path is invalid.
     */
    public ApiLimit getLimitInfo(String urlPath) throws InvalidUrlException {
        // 分割字符串
        List<String> pathDirs = UrlUtils.tokenizeUrlPath(urlPath);
        Node cur = root;
        for (int i = 0; i < pathDirs.size(); i++) {
            ConcurrentHashMap<String, Node> edges = cur.edges;
            Node node = edges.get(pathDirs.get(i));
            if (node == null) {
                return null;
            }
            // 如果为最后一个元素,设置限流
            if (i == (pathDirs.size() - 1)) {
                return node.getApiLimit();
            }

            cur = node;
        }
        return null;
    }

    public static final class Node {

        private String pathDir;

        private boolean isPattern;

        private ConcurrentHashMap<String, Node> edges = new ConcurrentHashMap<>();

        private ApiLimit apiLimit;

        public Node() {
        }

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
