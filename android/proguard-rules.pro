# Providers use their name to load files from META-INF
-keepnames class io.grpc.ServerProvider
-keepnames class io.grpc.ManagedChannelProvider
-keepnames class io.grpc.NameResolverProvider

# The Provider implementations must be kept and retain their names, since the
# names are referenced from META-INF
-keep class io.grpc.internal.DnsNameResolverProvider
-keep class io.grpc.okhttp.OkHttpChannelProvider
