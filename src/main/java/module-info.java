@org.jspecify.nullness.NullMarked
module com.github.benmanes.caffeine {
  exports com.github.benmanes.caffeine.cache;
  exports com.github.benmanes.caffeine.cache.stats;

  requires static com.google.errorprone.annotations;
  requires static org.checkerframework.checker.qual;
  requires static org.jspecify;
}
