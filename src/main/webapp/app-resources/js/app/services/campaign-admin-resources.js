var app = angular.module('sterling.services', ['ngResource', 'ngCookies']);
var direct = angular.module('sterling.directives', []);


app.factory('NewsFeed', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/';

    return $resource(urlBase + ':id', {id: '@id'}, {

        query:  {
            method : 'GET', isArray : false
          },
          addViewForArticle: {
            method: 'OPTIONS',
            data: '',
            params: {newsFeedArticle: '@id'}
          }

     });

}]);

app.factory('SearchServices', ['$http', function($http)
{

    var searchServices = {};

    searchServices.search = function(searchItem, urlBase, cb)
    {

        $http({

            method: 'GET',
            url: urlBase,
            params: {search: searchItem}

        }).then(function(response)
        {

            cb(response.data);

        });

    };

    return searchServices;

}]);

app.factory('Dashboard', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/dashboard/';

    return $resource(urlBase + ':id', {id: '@id'}, {

        query:  {
            method : 'GET', isArray : false
          },
          showAllAuthor: {
            method: 'GET',
            isArray : false,
            params: {show: '@id'}
          }

     });

}]);


app.factory('NewsFeedArticle', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/article/';

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          inactiveComment: {

            method: 'OPTIONS',
            params: {commentId: '@articleId'}

          }
     });

}]);

app.factory('newsFeedArticleServices', ['$http', function($http)
{

    var newsFeedArticle = {};

    newsFeedArticle.inactiveComment = function(commentId, urlBase, cb)
    {

        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {commentId: commentId}

        }).then(function(response)
        {

            cb(response.data);

        });

    };

    newsFeedArticle.fetchAllComments = function(articleId, urlBase, cb)
    {
        $http({

            method: 'GET',
            url: urlBase,
            params: {articleId: articleId}

        }).then(function(response)
        {

            cb(response.data);

        });
    };

    return newsFeedArticle;

}]);

app.factory('NewsFeedQuestion', ['$resource', '$window', '$stateParams', function($resource, $window, $stateParams)
{
    var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/';

    return $resource(urlBase + ':questionId', {questionId: '@questionId'}, {

        query:  {
            method : 'GET', isArray : false
          }

     });

}]);

// finish this code for more reliable function
app.factory('NewsFeedQuestionAnswer', ['$resource', '$window', '$stateParams', function($resource, $window, $stateParams)
{
    var qId = $stateParams.id;
    var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' + qId + '/';

    return $resource(urlBase + ':questionId', {questionId: '@questionId'},

     {answer: {method: 'POST', params: {answer: true}}

     });
     // query:  {
     //     method : 'GET', isArray : false
     //   },
}]);

app.factory('NewsFeedQuestionAnswerServices', ['$http', function($http)
{

    var newsFeedQuestionAnsServices = {};

    newsFeedQuestionAnsServices.getComments = function(questAnsId, urlBase, cb)
    {
        $http({

            method: 'GET',
            url: urlBase,
            params: {questionAnswerId: questAnsId}

        }).then(function(response)
        {
            cb(response);
        });
    };

    newsFeedQuestionAnsServices.submitComment = function(comment, questAnsId, urlBase, cb)
    {

        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {comment: comment, questionAnswerId: questAnsId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    return newsFeedQuestionAnsServices;

}]);

app.factory('DownloadFileServices', ['$http', '$window', '$timeout', '$q', function($http, $window, $timeout, $q)
{

    var downloadFileService = {};

    downloadFileService.downloadFile = function(urlBase)
    {
      var defer = $q.defer();

                  $timeout(function() {
                    window.open(urlBase, '_blank', '');
                          // $window.location = urlBase;

                      }, 1000)
                      .then(function() {
                          defer.resolve('success');
                      }, function() {
                          defer.reject('error');
                      });
                  return defer.promise;
    };
    return downloadFileService;

}]);


app.factory('AnswerQuestionServices', ['$http', '$stateParams', '$window', function($http, $stateParams, $window)
{
      var id = $stateParams.id;
      var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' + id + '/';
      var anwserServices = {};

      anwserServices.submitAnswer = function(requestParam, questionId, cb)
      {

          $http({

              url: urlBase,
              method: 'POST',
              data: '',
              params: {answer: requestParam, questionId: questionId}

          }).then(function(response)
          {

              cb(response);

          });

      };

      anwserServices.isAnswered = function(questionId, cb)
      {

          $http({

              url: urlBase,
              method: 'GET',
              data: '',
              params: {questionId: questionId}

          }).then(function(response)
          {

              cb(response);

          });

      };
      return anwserServices;

}]);


app.factory('AllArticle', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/all-article/';

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          getArticle: {

            method: 'GET',
            params: {article: '@articleId'}

          },
          pinArticle: {

            method: 'OPTIONS',
            data: '',
            params: {article: '@articleId'}

          },
          inactiveArticle: {
            method: 'OPTIONS',
            params: {inactiveArticle: '@articleId'}
          },
          selectedCategory: {
            method: 'OPTIONS',
            params: {articleCategory: '@articleId'}
          }
     });

}]);

//app.factory('AllArticleServices', ['$http', '$window' function($http, $window)
//{
//
//    var urlBase = $window.location.origin + $window.location.pathname + '/all-article';
//
//    var articleServices = {};
//
//    articleServices
//
//}]);

app.factory('ArticleWriting', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/article/';

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          saveUpdateInitial: {
            method: 'POST'
          }


     });

}]);

app.factory('PendingArticles', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/pending-articles';

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          getPendingArticle: {
            method: 'GET',
            params: {article: '@articleId'}
          }

     });

}]);

app.factory('OnePendingArticle', ['$resource', '$window', '$stateParams', function($resource, $window, $stateParams)
{
    var id = $stateParams.articleId
    var urlBase = $window.location.origin + $window.location.pathname + '/pending-articles/' + id;

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          getPendingArticle: {
            method: 'GET',
            params: {article: '@articleId'}
          },
          approveArticle: {
            method: 'OPTIONS',
            params: {approve: '@articleId'}
          }

     });

}]);

app.factory('OnePendingServices', ['$http', '$window', function($http, $window)
{

    var pendingServices = {};

    pendingServices.saveRejection = function(articleId, reason, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {articleId: articleId, reason: reason}

        }).then(function(response)
        {
            cb(response.data);
        });

    };

    return pendingServices;

}]);

app.factory('DraftArticles', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/my-draft-articles/';

    return $resource(urlBase + ':draftArticleId', {draftArticleId: '@draftArticleId'}, {

        query:  {
            method : 'GET', isArray : false
          },

     });

}]);

app.factory('ArticleWritingServices', ['$http', '$window', function($http, $window)
{

    var articleServingFactory = {};

    articleServingFactory.saveArticle = function(draftArticleId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'OPTIONS',
            params: {draftArticleId: draftArticleId}

        }).then(function(response)
        {
            cb(response);
        });

    };
    articleServingFactory.deleteDraftArticle = function(draftArticleId, urlBase, cb)
    {

        $http({
          url: urlBase,
          method: 'DELETE',
          params: {draftArticleId: draftArticleId}

        }).then(function(response)
        {

            cb(response);

        });
    };
    articleServingFactory.getDraftArticle = function(draftArticleId, urlBase, cb)
    {

        $http({

            url: urlBase,
            method: 'GET',
            params: {updateDraft: draftArticleId}

        }).then(function(response)
        {
            cb(response);
        });

    };

    return articleServingFactory;

}]);

app.factory('DraftingArticles', ['$resource', '$window', '$stateParams', function($resource, $window, $stateParams)
{
    var id = $stateParams.draftArticleId;
    var urlBase = $window.location.origin + $window.location.pathname + '/article/' + id;

    return $resource(urlBase + ':draftArticleId', {draftArticleId: '@draftArticleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          updateDraftArticle: {
              method: 'PUT'
          },
          deleteDraftArticle: {
              method: 'DELETE',
              params: {draftArticleId: '@draftArticleId'},
              headers :{'Content-Type':'application/json'}
          }


     });

}]);

app.factory('DraftsArticleWriting', ['$http', '$window', function($http, $window)
{

    var draftsArticleWriting = {}

    draftsArticleWriting.saveInitialDraft = function(draftId, title, category, draftSummernote, urlBase, cb)
    {

      $http({
        url: urlBase,
        method: 'POST',
        data: '',
        params: {draftId: draftId, title: title, category: category, draftSummernote: draftSummernote},
        headers :{'Content-Type':'application/json'}

      }).then(function(response)
      {
          cb(response);
      });

    };
    draftsArticleWriting.saveDraftPerMinutes = function(formParams, urlBase, cb)
    {
        $http({
          url: urlBase,
          method: 'OPTIONS',
          data: formParams,
          // params: {draftId: draftArticleId}

        }).then(function(response)
        {
            cb(response);
        });
    }
    return draftsArticleWriting;

}]);
// app.factory('DraftsArticleWriting', ['$resource', '$window', function($resource, $window)
// {
//
//     var urlBase = $window.location.origin + $window.location.pathname + '/article/';
//
//     return $resource(urlBase + ':title/:category/:draftSummernote', {} {
//
//         query:  {
//             method : 'GET', isArray : false
//           },
//           saveInitialDraft: {
//               url: urlBase + ':title/:category/:draftSummernote',
//               method: 'POST',
//               data: '',
//               params: {title: '@title', category: '@category', draftSummernote: '@draftSummernote'}
//           }
//           // saveDraftPerMinutes: {
//           //     method: 'OPTIONS',
//           //     params: {draftArticleId: '@articleId', title: '@title', category: '@category', draftSummernote: '@draftSummernote'}
//           // }
//
//      });
//
// }]);

// app.factory('newsFeedArticle', ['$resource', '$window', function($resource, $window)
// {
//
//     var urlBase = $window.location.origin + $window.location.pathname + '/news-feed-articles/';
//
//     return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {
//
//         query:  {
//             method : 'GET', isArray : false
//           }
//
//      });
//
// }]);

app.factory('MyArticles', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/my-articles/';

    return $resource(urlBase + ':articleId', {articleId: '@articleId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          inactiveArticle: {
            method: 'OPTIONS',
            params: {inactiveArticle: '@articleId'}
          }

     });

}]);

app.factory('AllQuestion', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/all-question/';

    return $resource(urlBase + ':questionId', {questionId: '@questionId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          inactiveQuestion: {
            method: 'OPTIONS',
            params: {inactiveQuestion: '@questionId'}
          }

     });

}]);

app.factory('MyQuestions', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/questions/my-questions/';

    return $resource(urlBase + ':questionId', {questionId: '@questionId'}, {

        query:  {
            method : 'GET', isArray : false
          }

     });

}]);

app.factory('People', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/people/';

    return $resource(urlBase + ':people', {people: '@people'}, {

        query:  {
            method : 'GET', isArray : false
          }

     });

}]);

app.factory('Category', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/categories/';

    return $resource(urlBase + ':categoryId', {categoryId: '@categoryId'}, {

      query:  {
          method : 'GET', isArray : false
        },
        updateCategory: {
           method: 'OPTIONS',
           params: {category: 'categoryId'}
         }

    });

}]);

app.factory('ManageCategoryResources', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/manage-categories/';

    return $resource(urlBase + ':id', {id: '@id'}, {

      query:  {
          method : 'GET', isArray : false
        }

    });

}]);
app.factory('ManageCategoryServices', ['$http', function($http)
{

    var manageCategory = {};

    manageCategory.updateCategory = function(articleId, moveTo, urlBase, cb)
    {

        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {articleId: articleId, moveTo: moveTo}

        }).then(function(response)
        {

            cb(response.data);

        });

    };

    return manageCategory;

}]);

app.factory('CategoryServices', ['$http', function($http)
{

    var categoryServices = {};

    categoryServices.updateCategory = function(newCategory, oldCategory, urlBase, cb)
    {

        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {newCat: newCategory, oldCat: oldCategory}

        }).then(function(response)
        {

            cb(response.data);

        });

    };

    categoryServices.deleteCategory = function(categoryName, urlBase, cb)
    {

        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {categoryName: categoryName}

        }).then(function(response)
        {
            cb(response.data);
        });

    };


    return categoryServices;

}]);


app.factory('MyProfile', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/my-profile';

    return $resource(urlBase + ':empDetailsId', {empDetailsId: '@empDetailsId'}, {

        query:  {
            method : 'GET', isArray : false
          },
          uploadAvatar: {

              method: 'OPTIONS',
          },
          updatePassword: {
              method: 'OPTIONS',
              params: {password: '@empDetailsId'},
        },
          updatePosition: {
              method: 'OPTIONS',
              params: {position: '@empDetailsId'}
          },
          updateEmail: {
              method: 'OPTIONS',
              params: {email: '@empDetailsId'}
          },
          updateContactNumber: {
              method: 'OPTIONS',
              params: {contactNumber: '@empDetailsId'}
          },
          updateGender: {
              method: 'OPTIONS',
              params: {gender: '@empDetailsId'}
          }
     });

}]);

app.factory('ProfilePicUpload', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/my-profile/';

    return $resource(urlBase + ':pictureId', {pictureId: '@pictureId'}, {

        updateCategory: { method: 'PUT' }

    });

}]);

app.factory('NewsResources', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/news';

    return $resource(urlBase + ':draftNewsId', {draftNewsId: '@draftNewsId'}, {

      query:  {
          method : 'GET', isArray : false
        },
        inactiveNews: {

          method: 'OPTIONS',
          params: {inactiveNews: '@draftNewsId'}

        }

    });

}]);


// app.factory('NewsResources', ['$resource', '$window', function($resource, $window)
// {
//
//     var urlBase = $window.location.origin + $window.location.pathname + '/news';
//
//     return $resource(urlBase + ':draftNewsId', {draftNewsId: '@draftNewsId'}, {
//
//       query:  {
//           method : 'GET', isArray : false
//         }
//
//     });
//
// }]);

app.factory('NewsCreateResources', ['$resource', '$window', '$stateParams', function($resource, $window, $stateParams)
{

    var id = $stateParams.draftNewsId;
    var urlBase = $window.location.origin + $window.location.pathname + '/news/' + id;

    return $resource(urlBase + ':draftNewsId', {draftNewsId: '@draftNewsId'}, {

      query:  {
          method : 'GET', isArray : false
        },
        updateDraftNews: {
            method: 'PUT'
        },
        getCurrentDraftNews: {
          method: 'GET',
          params: {draftNewsId: '@draftNewsId'}
        }
    });

}]);

app.factory('OneNewsServices', ['$http', function($http)
{

    var oneNews = {};

    oneNews.getOneNews = function(id, urlBase, cb)
    {

        $http({

            method: 'GET',
            url: urlBase,
            params: {newsId: id}

        }).then(function(response)
        {

            cb(response.data);

        });

    };

    return oneNews;

}]);

app.factory('NewsServices', ['$http', function($http)
{
    var newsServices = {};

    newsServices.getCurrentDraftNews = function(id, urlBase, cb)
    {
        $http({

            method: 'GET',
            url: urlBase,
            params: {draftNewsId: id}

        }).then(function(response)
        {

            cb(response.data);

        });
    };

    newsServices.saveNews = function(id, urlBase, cb)
    {
        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {draftNewsId: id}

        }).then(function(response)
        {

            cb(response);

        });
    };

    return newsServices;

}]);

app.factory('DraftNewsResources', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/draft-news';

    return $resource(urlBase + ':draftNewsId', {draftNewsId: '@draftNewsId'}, {

      query:  {
          method : 'GET', isArray : false
        }

    });

}]);

app.factory('EventResources', ['$resource', '$window', function($resource, $window)
{

    var urlBase = $window.location.origin + $window.location.pathname + '/events';

    return $resource(urlBase + ':eventId', {eventId: '@eventId'}, {

      query:  {
          method : 'GET', isArray : false
        },
        eventAttendance: {
          method: 'OPTIONS',
          params: {eventId: '@eventId'}
        }

    });

}]);

app.factory('EventServices', ['$http', function($http)
{

    var eventServices = {};

    eventServices.selectEvent = function(id, urlBase, cb)
    {

        $http({

            method: 'GET',
            url: urlBase,
            params: {eventId: id}

        }).then(function(response)
        {
            cb(response.data);
        });

    };
    eventServices.eventAttendance = function(id, urlBase, cb)
    {
        $http({

            method: 'OPTIONS',
            url: urlBase,
            params: {evtId: id}

        }).then(function(response)
        {
            cb(response.data);
        });
    };

    return eventServices;

}]);

app.factory('LoginServces', ['$http', '$window', '$rootScope', '$cookies', function($http, $window, $rootScope, $cookies)
{
    // this will get the absolute url
    var urlBase = $window.location.origin + $window.location.pathname;

    var loginServices = {};

    loginServices.setCredentials = setCredentials;
    loginServices.setCampaignCredential = setCampaignCredential;
    loginServices.clearCredentials = clearCredentials;


    loginServices.login = function(username, password, cb)
    {

        $http({

           url: urlBase + username + '/' + password,
           method: 'GET'

        }).then(function(result)
        {

            cb(result);

        });

    };

    function setCredentials(username, password)
    {


        var authenticationData = Base64.encode(username + ':' + password);


        $rootScope.globals = {

            currentUser: {

                username: username,
                authenticationData: authenticationData

            }

        };


        $http.defaults.headers.common['Authorization'] = 'Basic ' + authenticationData;

        var cookieExpire = new Date();

        cookieExpire.setDate(cookieExpire.getDate + 7);
        $cookies.putObject('globals', $rootScope.globals, { expires: cookieExpire});



    };

    function setCampaignCredential(campaignName)
    {
        var authenticationCampId = campaignName;
        $rootScope.campGlobals = {

            currentCampaign: {

                campaignName: campaignName,
                authenticationCampId: authenticationCampId

            }

        };

        $http.defaults.headers.common['Authorization'] = 'Basic ' + authenticationCampId;
        var cookieExpire = new Date();
        cookieExpire.setDate(cookieExpire.getDate + 7);
        $cookies.putObject('campGlobals', $rootScope.campGlobals, { expires: cookieExpire});

    };

    function clearCredentials()
    {

        $rootScope.globals = {};
        $cookies.remove('globals');
        $http.defaults.headers.common.Authorization = 'Basic';

    };


    // Base64 encoding service used by AuthenticationService
    var Base64 = {

        keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',

        encode: function (input)
        {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do
            {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2))
                {
                    enc3 = enc4 = 64;
                }
                else if (isNaN(chr3))
                {
                    enc4 = 64;
                }

                output = output +
                    this.keyStr.charAt(enc1) +
                    this.keyStr.charAt(enc2) +
                    this.keyStr.charAt(enc3) +
                    this.keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            }
            while (i < input.length);

            return output;
        },

        decode: function (input)
        {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input))
            {
                window.alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            do
            {
                enc1 = this.keyStr.indexOf(input.charAt(i++));
                enc2 = this.keyStr.indexOf(input.charAt(i++));
                enc3 = this.keyStr.indexOf(input.charAt(i++));
                enc4 = this.keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64)
                {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64)
                {
                    output = output + String.fromCharCode(chr3);
                }

                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";

            }
            while (i < input.length);

            return output;
        }
    };

    return loginServices;

}]);
