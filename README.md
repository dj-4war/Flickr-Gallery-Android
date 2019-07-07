# Flickr-Gallery-Android
Purpose of this project is build a Flickr Gallery for Public posts and show the details of them.

#Features :
- Get Flickr live public posts.
- Show them on Gallery view.
- Show meta data of a post.
- Feed details view on every post.
- Refresh feeds capability.
- User Profile view capability.

Application uses Flickr public post APIs to get the posts. End point responds with XML response with bunch of feeds application responsibility to
parse them and use in gallery.

UI :
All the latest post with meta data will be rendered in Gallery view. There are 2 approaches galley item can be shown i.e with WebView and Custom
data view.
- We can render feed HTML as is in List item by using WebView in item. In this approach we don't get much control over the view UI wil be poor this case
- Parse the data and design List item view as we need.  With this UI can be fully customized because we parse the data and Models hold the data, overhead here is
  we need to parse HTML to get the data from content of the feed. Current code does #2 route. Still the views and code is inplace for #1
  if we want to try.

Controllers :
FeedController is responsible to provide the and process the data.

Data flows through RxBus, controller publish the data as when controller process the data.




