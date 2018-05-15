var app = angular.module('sterling.controllers', ['ui.router', 'ngAnimate', 'ngCookies', 'ngMessages', 'chart.js', 'naif.base64', 'ui.bootstrap', 'LocalStorageModule', 'ngStorage', 'angular-carousel']);

var direct = angular.module('sterling.directives', []);

app.controller('campaignAdminPageController', ['$scope', '$rootScope', '$cookies', '$state', '$window', '$http', 'LoginServces', '$transitions', '$log', 'localStorageService',
function($scope, $rootScope, $cookies, $state, $window, $http, LoginServces, $transitions, $log, localStorageService)
{
    $scope.avatar = null;
    $scope.fetchAvatar = function()
    {
        $scope.avatar = localStorageService.get('avatar');
    };
    $scope.fetchAvatar();

    $scope.initAvatar = function()
    {
        if($scope.avatar !== null)
        {
            $scope.avatarFileType = $scope.avatar.fileType;
            $scope.avatarBase64 = $scope.avatar.base64;
        }
        else
        {
            // $scope.avatarFileType = $scope.avatar.fileType;
            // $scope.avatarBase64 = $scope.avatar.base64;
        }

    };
    $rootScope.$on('UpdatedAvatar', function(evt, args)
    {
        if(args.updatedAvatar == true)
        {
            $scope.avatar = localStorageService.get('avatar');
            $scope.avatarFileType = $scope.avatar.fileType;
            $scope.avatarBase64 = $scope.avatar.base64;
        }

    });



    $scope.$watchGroup(['avatar.fileType', 'avatar.base64', 'avatar.fileName'], function(newValue, oldValue)
    {
        var nValue = newValue[2];
        var oValue = oldValue[2];
        $scope.avatar = localStorageService.get('avatar');
        if(nValue !== oValue)
        {
            $scope.avatarFileType = $scope.avatar.fileType;
            $scope.avatarBase64 = $scope.avatar.base64;
        }
        if($scope.avatar == null)
        {
            $scope.defaultAvatar = true;
        }
    }, true);


    var cookie = $cookies.getObject('globals');
    var campaign = $cookies.getObject('campGlobals');
    var empName = $cookies.getObject('empName');
    $scope.username = cookie.currentUser.username;
    $scope.campaign = campaign.currentCampaign.campaignName;
    $scope.empName = empName;
    $scope.newsFeed = function()
    {

        $state.go('newsFeed');

    };

    $scope.dashboard = function()
    {

        $state.go('dashboard');

    };

    $scope.allNews = function()
    {

        $state.go('allNews');

    };


    $scope.draftNews = function()
    {

        $state.go('draftNews');

    };

    $scope.newArticles = function()
    {

        $state.go('new-articles');

    };

    $scope.allArticle = function()
    {

        $state.go('all-articles');

    };

    $scope.myArticles = function()
    {

        $state.go('my-articles');

    };
    $scope.article = function()
    {
        $state.go('article');
    };

    $scope.pendingArticles = function()
    {

        $state.go('pendingArticles');

    };

    $scope.myDraftArticles = function()
    {
        $state.go('myDraftArticles');
    }

    $scope.events = function()
    {
        $state.go('events');
    };

    $scope.allQuestion = function()
    {

        $state.go('questions');

    };

    $scope.newQuestions = function()
    {

        $state.go('new-questions');

    };

    $scope.myQuestions = function()
    {

        $state.go('my-questions');

    };

    $scope.people = function()
    {
        $state.go('people');
    }

    $scope.myProfile = function()
    {
        $state.go('my-profile');
    };

    $scope.categories = function()
    {

        $state.go('articleCategories');

    };

    $scope.logout = function()
    {
      console.log('Logout');
     var urlBase = $window.location.origin + $window.location.pathname;
     var replace = $window.location.pathname.replace('campaign-admin', '') ;

       $http({

          url: replace + 'logout',
          method: 'GET',
          headers: {

              'Content-Type': 'application/json;charset=ISO-8859-1'

          }

        }).then(function()
        {

            console.log(replace + 'logout');
            $window.location.href = replace + 'logout';
            localStorageService.remove('avatarFileType');
            localStorageService.remove('avatarbase64');
            LoginServces.clearCredentials();

        });

    };

}]);



app.controller('searchController', ['SearchServices', '$scope', '$state', '$window', 'localStorageService', '$timeout',
 function(SearchServices, $scope, $state, $window, localStorageService, $timeout)
{

    $scope.wideSearch = '';
    $scope.searched = '';
    $scope.showSearchedResult = false;
    $('#caSearchLoader').fadeIn();
    $scope.searchFilter = 'all';
    $scope.initialSearch = function()
    {
        var initialSearch = localStorageService.get('search');
        var urlBase = $window.location.origin + $window.location.pathname + '/search';
        SearchServices.search(initialSearch, urlBase, function(response)
        {
            $('#caSearchLoader').fadeOut();
            $scope.searchedResults = response.searchResult;

            $timeout(function()
            {

                $scope.showSearchedResult = true;

            }, 2000);
        });

        $scope.searched = localStorageService.get('search');

    }
    $scope.initialSearch();

      $scope.selectSearched = function(id, type)
      {
          if(type === 'article')
          {
              $state.go('showNewsFeedArticle', {

                  id: id,
                  type: type

              });
          }
          else if(type === 'question')
          {

              $state.go('showNewsFeedQuestion', {

                  id: id,
                  type: type

              });

          }
          else if(type === 'news')
          {
              $state.go('showNews', {

                  newsId: id

              });
          }
      }

    $('#searchAll').css({ 'background': '#F6F7F2' });

    $scope.filterToAll = function()
    {
        $scope.searchFilter = 'all';
        $('#searchAll').css({ 'background': '#F6F7F2' });
        $('#searchArticle').css({ 'background': '#fff' });
        $('#searchQuestion').css({ 'background': '#fff' });
        $('#searchNews').css({ 'background': '#fff' });
        $('#searchEvent').css({ 'background': '#fff' });
    };

    $scope.filterToArticles = function()
    {
        $scope.searchFilter = 'article';
        $('#searchAll').css({ 'background': '#fff' });
        $('#searchArticle').css({ 'background': '#F6F7F2' });
        $('#searchQuestion').css({ 'background': '#fff' });
        $('#searchNews').css({ 'background': '#fff' });
        $('#searchEvent').css({ 'background': '#fff' });
    };

    $scope.filterToQuestion = function()
    {
        $scope.searchFilter = 'question';
        $('#searchAll').css({ 'background': '#fff' });
        $('#searchArticle').css({ 'background': '#fff' });
        $('#searchQuestion').css({ 'background': '#F6F7F2' });
        $('#searchNews').css({ 'background': '#fff' });
        $('#searchEvent').css({ 'background': '#fff' });
    };

    $scope.filterToNews = function()
    {
        $scope.searchFilter = 'news';
        $('#searchAll').css({ 'background': '#fff' });
        $('#searchArticle').css({ 'background': '#fff' });
        $('#searchQuestion').css({ 'background': '#fff' });
        $('#searchNews').css({ 'background': '#F6F7F2' });
        $('#searchEvent').css({ 'background': '#fff' });
    };

    $scope.filterToEvents = function()
    {
        $scope.searchFilter = 'event';
        $('#searchAll').css({ 'background': '#fff' });
        $('#searchArticle').css({ 'background': '#fff' });
        $('#searchQuestion').css({ 'background': '#fff' });
        $('#searchNews').css({ 'background': '#fff' });
        $('#searchEvent').css({ 'background': '#F6F7F2' });
    };

    $scope.goSearch = function()
    {
        $scope.showSearchedResult = false;
        $('#caSearchLoader').fadeIn();
        $scope.searched = $scope.wideSearch;
        localStorageService.set('search', $scope.wideSearch);
        var search = localStorageService.get('search');
        var urlBase = $window.location.origin + $window.location.pathname + '/search';
        SearchServices.search(search, urlBase, function(response)
        {
            $('#caSearchLoader').fadeOut();
            $scope.searchedResults = response.searchResult;
            console.log($scope.searchedResults);
            $timeout(function()
            {

                $scope.showSearchedResult = true;

            }, 2000);
        });
    };


}]);

app.controller('newsFeedController', ['NewsFeed', '$scope', '$state', '$timeout', '$rootScope', '$window', 'localStorageService',
 function(NewsFeed, $scope, $state, $timeout, $rootScope, $window, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.newsFeedObjects = [];
    $scope.pageSize = 20;
    $scope.currentPage = 1;

    $scope.showNewsFeed = false;
    $scope.fetchAllNewsFeed = function()
    {

        $scope.responsed = NewsFeed.query(function(response)
        {
            console.log(response);
            if(response.hasNewsFeed === 'OK')
            {
                $scope.newsFeedObjects = response.newsFeedObjects;
                response.newsFeedObjects.forEach(function(data)
                {
                    data.date = new Date(data.dateCreated);

                });
            }
            else if(response.hasNewsFeed === 'NO_CONTENT')
            {
                console.log('No News Feed');
            }

            $('#newsFeedLoader').fadeOut();
            $timeout(function()
            {


                if(response.hasPinToTopArticles === 'OK')
                {
                    console.log($scope.pinToTopArticles);
                    $scope.pinToTopArticles = response.pinToTopArticles;
                    $scope.pinToTopArticles.forEach(function(data)
                    {
                        data.date = new Date(data.dateCreated);
                    });
                    $scope.limitPinToTop = 10;
                    $scope.pin = response;
                    var pin = $scope.pin.hasPinToTopArticles;
                    if(pin === 'OK')
                    {
                        $scope.showPinToTop = true;
                    }
                    else if(pin === 'NO_CONTENT')
                    {
                        $scope.showPinToTop = false;
                    }

                }
                else if(response.hasPinToTopArticles === 'NO_CONTENT')
                {

                }

                $scope.showNewsFeed = true;

            }, 1000);
        });

    };
    $scope.fetchAllNewsFeed();


    $scope.goToDetails = function(id, type)
    {

        if(type === 'article')
        {
            $state.go('showNewsFeedArticle', {

                id: id,
                type: type

            });
        }
        else if(type === 'question')
        {

            $state.go('showNewsFeedQuestion', {

                id: id,
                type: type

            });

        }
        else if(type === 'news')
        {
            $state.go('showNews', {

                newsId: id

            });
        }
        else
        {
            // redirect to error page
        }

    };

    $scope.addViewForArticle = function(id, type)
    {
        if(type === 'article')
        {
            NewsFeed.addViewForArticle({newsFeedArticle: id}, function(response)
            {
                console.log(response);
            });
        }

    };

    $scope.addViewForArticleFromPin = function(id, type)
    {
        if(type === 'article')
        {
            NewsFeed.addViewForArticle({newsFeedArticle: id}, function(response)
            {
                console.log(response);
            });
        }
    };

    $scope.backTobewsFeed = function()
    {
        $state.go('newsFeed');
    };

}]);

app.controller('dashboardController', ['Dashboard', 'SearchServices', '$scope', '$timeout', 'localStorageService', '$rootScope', '$window', '$state',
 function(Dashboard, SearchServices, $scope, $timeout, localStorageService, $rootScope, $window, $state)
{

    $scope.wideSearch = '';

    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };



    $scope.categoryLabels = [];
    $scope.categoryDatas = [];
    $('#caDashboardLoader').fadeIn();
    $scope.fetchAllData = function()
    {

        $scope.response = Dashboard.query(function(response)
        {

            if(response.hasAnAvatar === 'OK')
            {
                $scope.userAvatar = response.userAvatar;
                localStorageService.set('avatarFileType', $scope.userAvatar.fileType);
                localStorageService.set('avatarbase64', $scope.userAvatar.base64);

                $rootScope.$emit('UpdatedAvatar', {

                    updatedAvatar: true

                });

            }

            $scope.articleStatistics = response.articleStatistics;

            angular.forEach($scope.articleStatistics, function(value, key)
            {

                $scope.categoryLabels.push(value.accountName);
                $scope.categoryDatas.push(value.numberOfArticle);

            });

            $scope.campaignStatistics = response;

            if($scope.campaignStatistics.hasContributers === 'OK')
            {
                $scope.contributers = response.contributers;
            }
            else if($scope.campaignStatistics.hasContributers === 'NO_CONTENT')
            {
            }

            if(response.hasMostView === 'OK')
            {
                $scope.articlesMostView = response.mostView;
            }
            else if(response.hasMostView === 'NO_CONTENT')
            {
            }

            if(response.hasMostRecentlyAdded === 'OK')
            {
                $scope.articlesRecentlyAdded = response.articleMostRecentlyAddeds;
            }
            if(response.hasMostRecentlyRevised === 'OK')
            {
                $scope.articleRecentlyReviseds = response.articleRecentlyReviseds;
                console.log($scope.articleRecentlyReviseds);
            }

            $scope.allAuthors = response.allAuthorWithNumOfArticle;
            console.log($scope.campaignStatistics);
        });
        $('#caDashboardLoader').fadeOut();

        $timeout(function()
        {
            $('#caDashboardLoader').fadeOut();
            $scope.showDashboard = true;
        }, 1000);
    };
    $scope.fetchAllData();

    $scope.showAllAuthor = false;
    $scope.showAllAuthorTable = false;
    $scope.showAllAuthorTable = function()
    {
        $('#caDashAllAuthors').fadeIn();
        var show = 'show';
        $('#loadAllAuthor').fadeIn();
        Dashboard.showAllAuthor({show: show}, function(response)
        {
            $('#loadAllAuthor').fadeOut();
            $timeout(function()
            {
                $scope.showAllAuthorTable = true;
                $scope.allAuthor = response.allAuthorWithNumOfArticle;

            }, 1000);
        });
    };

    $scope.hideAllAuthorTable = function()
    {
        $('#caDashAllAuthors').fadeOut();
    };

    $scope.goToUsers = function()
    {
        $state.go('people');
    };

    $scope.goToArticles = function()
    {
        $state.go('all-articles');
    };

    $scope.goToQuestions = function()
    {
        $state.go('questions');
    };

    $scope.goToCategories = function()
    {
        $state.go('articleCategories');
    };

    $scope.goToPendings = function()
    {
        $state.go('pendingArticles');
    };

    $scope.goToArticleSelected = function(id)
    {
        $state.go('showNewsFeedArticle', {

            id: id,

        });
    };


}]);

app.controller('newsFeedArticleController', ['NewsFeedArticle', 'DownloadFileServices', 'newsFeedArticleServices', '$scope', '$state', '$stateParams', '$http', '$window', '$timeout', '$rootScope', 'localStorageService',
 function(NewsFeedArticle, DownloadFileServices, newsFeedArticleServices, $scope, $state, $stateParams, $http, $window, $timeout, $rootScope, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.myComment = '';
    $scope.articleId = null;
    $scope.fileId = 0;
    if($stateParams.id)
    {
        $scope.articleId = $stateParams.id;
    }

    $scope.showNewsFeedArticle = false;
    $scope.fetchAllData = function()
    {

        $scope.oneArticle = NewsFeedArticle.get({articleId : $scope.articleId}, function(results)
        {
            $('#newsFeedArticleLoader').fadeOut();

            console.log(results);

            $scope.showNewsFeedArticle = true;
            $scope.authorName = results.authorName;
            $scope.authorAvatar = results.authorAvatar;
            $('.innerArticle').append(results.article.content);

            $scope.currentUserAvatar = results.currentUserAvatar;

            $rootScope.userAvatar = results.userAvatar;

            if(results.hasFileUploaded === 'OK')
            {
                $scope.articleDocument = results.articleDocument;
                $scope.fileId = $scope.articleDocument.fileId;
            }
            else if(results.hasFileUploaded === 'NO_CONTENT')
            {
                $scope.hideDownloadButton = true;
            }

            $rootScope.userAvatar = results.userAvatar;

        });

    };
    $scope.fetchAllData();

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.commentId = 0;
    $scope.showDeleteCommentPop = function(commentId)
    {
        $scope.commentId = commentId;
        $('#caDeleteCommentArticleNews').fadeIn();
    };

    $scope.closeDeletePopComment = function()
    {
        $scope.commentId = 0;
        $('#caDeleteCommentArticleNews').fadeOut();
        $timeout(function()
        {
            $scope.next('');
        }, 1000);
    };

    $scope.fetchAllComments = function()
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/article/' + $scope.articleId;
        newsFeedArticleServices.fetchAllComments($scope.articleId, urlBase, function(response)
        {
            $scope.comments = response.comments;
            if(response.hasComments === 'OK')
            {
                $scope.comments = response.comments;
                $scope.comments.forEach(function(data)
                {
                    data.date = new Date(data.dateCommented);
                });
            }
            else if(response.hasComments === 'NO_CONTENT')
            {

            }
        })

    };
    $scope.fetchAllComments();
    $scope.inactiveComment = function()
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/article/' + $scope.articleId;
        newsFeedArticleServices.inactiveComment($scope.commentId, urlBase, function(response)
        {
            $scope.fetchAllComments();
            $scope.next('details2');

        });
    };

    $scope.downloadFile = function()
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/download/' + $scope.fileId;

        $http({

            url: urlBase,
            method: 'GET',

        }).then(function(response)
        {
            console.log(response);
            var downloadPath = urlBase;
            window.open(downloadPath, '_blank', '');
        });

    };

    $scope.submitComment = function()
    {
        var myComment = $scope.myComment;
        var articleId = $scope.articleId;
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/article/' +  $scope.articleId + '/';
        $http({

            url: urlBase,
            method: 'POST',
            params: {comment: myComment, articleId: articleId},
            data: '',
            headers: {
                'Content-Type': 'text/plain'
            }

        }).then(function()
        {
            $scope.fetchAllComments();
        });

    };

    $scope.backToNewsFeed = function()
    {
        $state.go('newsFeed');
    };


}]);

app.controller('newsFeedQuestionController', ['NewsFeedQuestion', 'AnswerQuestionServices', 'NewsFeedQuestionAnswerServices', '$scope', '$state', '$stateParams', '$window', '$timeout', '$rootScope', 'localStorageService',
 function(NewsFeedQuestion, AnswerQuestionServices, NewsFeedQuestionAnswerServices, $scope, $state, $stateParams, $window, $timeout, $rootScope, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.newsFeedQuestionAnswer = new NewsFeedQuestion();

    $scope.questionId = null;
    if($stateParams.id)
    {
        $scope.questionId = $stateParams.id;
    }

    $scope.showForm = false;

    $scope.showSummernote = function(questionId)
    {
        AnswerQuestionServices.isAnswered(questionId, function(response)
        {
            if(response.data.notYetAnswered === 'OK')
            {
                $('#sFormQ').css({'opacity' : '0.5'});

                $scope.showForm = true;
            }
            else if(response.data.alreadyAnswered === 'CONFLICT')
            {
                $('#answeredPopBackground').fadeIn();
            }
        });

    };

    $('.dismiss').on('click', function()
    {

        $('#answeredPopBackground').fadeOut();

    });

    $scope.showOneQuestion = false;
    $scope.fetchAllData = function()
    {
        $scope.oneQuestion = NewsFeedQuestion.get({questionId : $scope.questionId}, function(response)
        {

            $scope.answers = response.answers;
            $timeout(function()
            {

                $scope.showOneQuestion = true;

            }, 1000);
            $('#caNewsFeedQuestionLoader').fadeOut();

        });
    };
    $scope.fetchAllData();

    $scope.convertHtml = function(html)
    {

        $('.innerAnsweredContent').append(html);

    };
    $scope.oneQuestion = NewsFeedQuestion.get({questionId : $scope.questionId}, function(response)
    {

    });

    // $scope.$on('$viewContentLoaded', function() {
    //
    //     angular.element(document).ready(function()
    //     {
    //
    //         $('#answerSummernote').summernote();
    //
    //     });
    //
    // });

    $('#submitAnswer').on('click', function()
    {

        var content = $("#answerQuestionForm").find("textarea[name = 'qAnsTexarea']").val();
        $scope.answer = content;

    });

    $scope.submitAnswer = function()
    {

      var questionId = $scope.questionId;
      var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' +  $scope.questionId + '/';

      AnswerQuestionServices.submitAnswer($scope.answer, questionId, function(response)
      {
          console.log(response);
          if(response.data.successfullyAnswered === 'OK')
          {
              $('#answeredPopBackground2').fadeIn();
              $scope.fetchAllData();
              $scope.showForm = false;

          }

      });

    };

    $('.dismiss').on('click', function()
    {

        $('#answeredPopBackground2').fadeOut();


    });

    $scope.showComment = false;

    $scope.showAllComment = function(questionAnswerId)
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' +  $scope.questionId + '/';
        $('.loadCommentInAnsQuest').fadeIn();
        NewsFeedQuestionAnswerServices.getComments(questionAnswerId, urlBase, function(response)
        {
            if(response.data.hasComments === 'OK')
            {
                $scope.comments = response.data.comments;
                $('.loadCommentInAnsQuest').fadeOut();
                $scope.showCommentBoxButton = true;

                $scope.comments.forEach(function(data)
                {
                    data.date = new Date(data.dateCommented);
                });

                $timeout(function()
                {
                    $scope.showComment = true;
                }, 1000);
            }
            else if(response.data.hasComments === 'NO_CONTENT')
            {
                $('.loadCommentInAnsQuest').fadeOut();
                $scope.showCommentBoxButton = true;
                $timeout(function()
                {
                    $scope.showComment = true;
                }, 1000);
            }



        });

    };

    $scope.comment = '';

    $scope.submitComment = function(questionAnswerId)
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' +  $scope.questionId + '/';
        var comment = $('textarea.ansQuestComment').val();
        NewsFeedQuestionAnswerServices.submitComment(comment, questionAnswerId, urlBase, function(response)
        {
            $scope.comments = response.data.comments;

            $scope.comments.forEach(function(data)
            {
                data.date = new Date(data.dateCommented);
            });

            $('textarea.ansQuestComment').val('');
        });

    };

    $scope.showHideMessage = 'Hide';

    $scope.showHideComments = function()
    {

        if($scope.showHideMessage === 'Hide')
        {
            $scope.hideComments = true;
            $scope.showHideMessage = 'Show all';
        }
        else if($scope.showHideMessage === 'Show all')
        {
            $scope.hideComments = false;
            $scope.showHideMessage = 'Hide';
        }

    };

}]);

app.controller('allArticleController', ['AllArticle', '$scope', '$filter', '$state', 'localStorageService', '$timeout',
 function(AllArticle, $scope, $filter, $state, localStorageService, $timeout)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.articles = [];
    $scope.pageSize = 6;
    $scope.currentPage = 1;
    $('#caAllArticleLoader').fadeIn();
    $scope.showAllArticle = false;
    $scope.fetchAllData = function()
    {

        $scope.responsed = AllArticle.query(function(results)
        {
            $('#caAllArticleLoader').fadeOut();
            $timeout(function()
            {
                $scope.showAllArticle = true;
                $scope.articles = results.articles;
            }, 2000);
        });
    };
    $scope.fetchAllData();

    $scope.articleId = '';
    $scope.title = '';
    $scope.showArticleDeletePop = function(articleId, title)
    {
        $scope.articleId = articleId;
        $scope.title = title;
        $('#caDeleteArticle').fadeIn();
    }

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.inactiveArticle = function()
    {
        var articleId = $scope.articleId;
        AllArticle.inactiveArticle({inactiveArticle: articleId}, function(response)
        {
            console.log(response);
            $scope.fetchAllData();
            $timeout(function()
            {
                $scope.next('details2');
                $('.aArticleOneArticleContainer').fadeOut();
            }, 1000);
        });
    };

    $scope.closeDeletePop = function()
    {
        $scope.articleId = '';
        $scope.title = '';
        $timeout(function()
        {
            $scope.next('');
        }, 1500);
        $('#caDeleteArticle').fadeOut();
    }

    $scope.closeDeletePop2 = function()
    {
        $scope.articleId = '';
        $scope.title = '';
        $('#caDeleteArticle').fadeOut();
    };

    $scope.articleCategory = "All Article";
    $scope.selectedCategory = function()
    {
        AllArticle.selectedCategory({articleCategory: $scope.articleCategory}, function(response)
        {
            $scope.articles = response.articles;
            console.log(response);
        });
    };

    function clearAppend()
    {
        $('.aArticleCon').html('');
    }
    $scope.selectArticle = function(articleId)
    {
        clearAppend();
        $scope.oneArticle = AllArticle.getArticle({article: articleId}, function(response)
        {
            $('#oneArticleFromAllArticle').fadeOut();
            var content = response.article.content;
            $('.aArticleCon').append(content);

            if(response.article.pushpin === 'Unpin')
            {
                $scope.showPinButton = true;
                $scope.showUnPinButton = false;
            }
            else if(response.article.pushpin === 'Pin')
            {
              $scope.showPinButton = false;
              $scope.showUnPinButton = true;
            }

        });
        $('.aArticleOneArticleContainer').fadeIn();

        var $target = $('html,body');
        $target.animate({scrollTop: $target.height()}, 1000);

    };

    $scope.pinArticle = function(articleId)
    {
        var id = articleId;
        AllArticle.pinArticle({article: id}, function(response)
        {
            $scope.pin = response.pushPin;
            if($scope.pin === 'pin')
            {
                $scope.showPinButton = false;
                $scope.showUnPinButton = true;
            }
            else if($scope.pin === 'unpin')
            {
                $scope.showPinButton = true;
                $scope.showUnPinButton = false;
            }
        });
    };


    function matchFirstChar(c, string)
    {
  		return (string.charAt(0) == c);
  	}

  	function removeFirstChar(string)
    {
  		  return string.slice(1);
  	}

  	function removeDash(label)
    {
    		if (matchFirstChar('-', label))
        {
    			return removeFirstChar(label);
    		}
    		return label;
  	}
  	function addDash(label)
    {
    		if (!matchFirstChar('-', label))
        {
    			return '-' + label;
    		}
    		return label;
  	}

    $scope.filterSort = function(element)
    {
    		if ($filter('filter')([element], $scope.filterTable).length > 0)
        {
    			   return 1;
    		}
    		return 2;
	  };

    $scope.singleSort = function(label)
    {
    		if ($scope.sortType == label)
        {
    			   $scope.sortReverse();
    		}
        else
        {
  			     $scope.sortType = label;
    		}
	  };

    $scope.sortReverse = function(set)
    {
    		set = set || false;
    		if (set || !matchFirstChar('-', $scope.sortType))
        {
    			   $scope.sortType = addDash($scope.sortType);
    		}
        else
        {
    			   $scope.sortType = removeDash($scope.sortType);
    		}
	  };

    $scope.singleSort = function(label)
    {
        if ($scope.sortType == label)
        {
            $scope.sortReverse();
        }
        else
        {
            $scope.sortType = label;
        }
    };

    $scope.sortDescend = function(label1, label2)
    {
          label2 = label2 || '';
          return ($scope.sortType == label1 || $scope.sortType == label2);
    };

    $scope.sortAscend = function(label1, label2)
    {
        label2 = label2 || '';
        return ($scope.sortType == ('-' + label1) || $scope.sortType == ('-' + label2));
    };





}])
.filter('startFrom', function(){

    return function(data, start){
        return data.slice(start);
    }

});

app.controller('articleController', ['ArticleWriting', 'DraftsArticleWriting', 'DraftingArticles', 'ArticleWritingServices', '$scope',
'$window', '$http', '$state', '$stateParams', '$interval', '$rootScope', '$timeout', 'localStorageService',
 function(ArticleWriting, DraftsArticleWriting, DraftingArticles, ArticleWritingServices, $scope, $window, $http,
    $state, $stateParams, $interval, $rootScope, $timeout, localStorageService)
{
    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.$on('$viewContentLoaded', function() {
        angular.element(document).ready(function()
        {

            $('#summernote').summernote();

            $('#inputFile').on('change', function()
            {

                var control = $('#inputMask');
                control.val(this.files[0].name);

                $scope.test = 'testing';
                $scope.$digest();
                console.log($scope.test);

            });


        });
    });

    $('#submitArticleButton').on('click', function()
    {

        var content = $("#articleForm").find("textarea[name = 'summernote']").val();
        $scope.articleWriting.content = content;

    });

    $scope.draftArticleWriting = new DraftingArticles();
    $scope.articleWriting = new ArticleWriting();

    // load all categories
    $scope.fetchAllData = function()
    {
        $scope.responseData = ArticleWriting.query(function(result)
        {

        });
    };
    $scope.fetchAllData();

    $scope.draftArticleId = '';

    if($stateParams.draftArticleId)
    {
        $scope.draftArticleId = $stateParams.draftArticleId;
    }

    // populate articlewiriting from default data or draft data
    $scope.populateArticleWriting = function()
    {
        var urlBase = $window.location.origin + $window.location.pathname + '/article/' + id;
        var id = $scope.draftArticleId;
        ArticleWritingServices.getDraftArticle(id, urlBase, function(response)
        {
            $scope.setTitle = function()
            {
                $scope.title = response.data.draftArticle.title;
            }
            $scope.setTitle();
            $scope.setCategory = function()
            {
                $scope.category = response.data.draftArticle.category;
            };
            $scope.setCategory();
            $scope.setSummernote = function()
            {
                $('#summernote').summernote('code', response.data.draftArticle.content);
            };
            $scope.setSummernote();
        });

    };
    $scope.populateArticleWriting();



    $scope.title = $scope.draftArticleWriting.title;
    $scope.category = $scope.draftArticleWriting.category;
    var dSummernote = $('textarea#summernote').val();
    $scope.draftSummernote = dSummernote;

    $scope.summernoteValue = '';

    // get the value of summernote eevery 1 seconds
    var getSummernoteValue = $interval(function()
    {

        $scope.summernoteValue = $('textarea#summernote').val();

    }, 1000);

    // delete draft article if nothing entered by user
    $scope.backToMyArticles = function()
    {
        $state.go('my-articles');
    };


    $scope.updateArticle = function()
    {
        $scope.$watchGroup(['title', 'category', 'summernoteValue'], function(newValue, oldValue)
        {
            $scope.draftArticleWriting.draftArticleId = $scope.draftArticleId;
            if(newValue != oldValue)
            {
                $scope.draftArticleWriting.title = $scope.title;
                $scope.draftArticleWriting.category = $scope.category;
                $scope.draftArticleWriting.draftSummernote = $scope.summernoteValue;
                DraftingArticles.updateDraftArticle({draftArticleId: $scope.draftArticleWriting.draftArticleId}, $scope.draftArticleWriting, function(response)
                {
                });

            }
        });


    };

    $scope.updateArticle();


    function next()
    {

      $scope.article = 'article2';

    };


    $scope.submit = function()
    {
        // value for validating the form
        var title = $(".articleInputBox input[name = 'title']").val();
        var category = $('.articleInputBox select').find(":selected").text();
        var summernote = $('textarea#summernote').val();

        var id = $scope.draftArticleId;
        var urlBase = $window.location.origin + $window.location.pathname + '/article/' + id;

        $('#savingArticleAndFile').fadeIn();
        $scope.showSavingArticleMsg = true;
        $scope.showSavingFileMsg = false;

        if($scope.articleWritingForm.$valid)
        {
            ArticleWritingServices.saveArticle(id, urlBase, function(response)
            {
                var articleCreatedId = response.data.articleIdCreated;

                if(response.data.savingArticle === 'CREATED')
                {
                    var deleteDraftArticle = {draftArticleId: id};
                    ArticleWritingServices.deleteDraftArticle(id, urlBase, function(response)
                    {
                          if(response.data.draftArticleDeleted === 'OK')
                          {
                              $scope.showSavingArticleMsg = false;
                              $scope.showSavingFileMsg = true;
                              var file = $scope.file;
                              var data = new FormData();
                              data.append('file', file);

                              $http({

                                url: urlBase,
                                method: 'POST',
                                data: data,
                                params: {articleId: articleCreatedId},
                                transformRequest: angular.identity,
                                transformResponse: angular.identity,
                                headers : {
                                'Content-Type': undefined
                                }

                              }).then(function(response)
                              {
                                 next();

                                 console.log(response);
                              });

                          }
                    });
                }
            });

        }
        else if(title === '')
        {
            $scope.error = true;
            $('.info-pop-header').text('Oooooops!')
            $('.info-pop-para').text('Please enter a title name');
            $('.dismiss').addClass('error-dismiss');
            $('#articlePopBackground').fadeIn();

            $('.dismiss').on('click', function()
            {

                $('#articlePopBackground').fadeOut();

            });
        }

        else if(category === null)
        {

            $scope.error = true;
            $('.info-pop-header').text('Oooooops!')
            $('.info-pop-para').text('Please select category');
            $('.dismiss').addClass('error-dismiss');
            $('#articlePopBackground').fadeIn();

            $('.dismiss').on('click', function()
            {

                $('#articlePopBackground').fadeOut();

            });

        }
        else if(summernote == null)
        {

            $scope.error = true;
            $('.info-pop-header').text('Oooooops!')
            $('.info-pop-para').text('Please write an article');
            $('.dismiss').addClass('error-dismiss');
            $('#articlePopBackground').fadeIn();

            $('.dismiss').on('click', function()
            {

                $('#articlePopBackground').fadeOut();

            });

        }

    };

    // var file = $scope.file;
    // var data = new FormData();
    // data.append('file', file);
    // $state.go('savingArticle',
    // {
    //     title: $scope.title,
    //     category: $scope.category,
    //     content: $scope.articleWriting.content,
    //     file: data,
    //     draftId: $scope.draftArticleId
    // });
  // var title = $(".articleInputBox input[name = 'title']").val();
  // var category = $('.articleInputBox select').find(":selected").text();
  // var summernote = $('textarea#summernote').val();
  //
  // if($scope.articleWritingForm.$valid)
  // {
  //     $('#savingArticleAndFile').fadeIn();
  //     $scope.showSavingArticleMsg = true;
  //     $scope.showSavingFileMsg = false;
  //     $scope.articleWriting.$save(function(response)
  //     {
  //
  //         console.log(response);
  //         if(response.successfullyCreated === 'CREATED')
  //         {
  //             $scope.showSavingArticleMsg = false;
  //             $scope.showSavingFileMsg = true;
  //
  //             var file = $scope.file;
  //             var data = new FormData();
  //             data.append('file', file);
  //             var urlBase = $window.location.origin + $window.location.pathname + '/article';
  //              $http({
  //
  //                url: urlBase,
  //                method: 'POST',
  //                data: data,
  //                transformRequest: angular.identity,
  //                transformResponse: angular.identity,
  //                headers : {
  //                'Content-Type': undefined
  //                }
  //
  //              }).then(function(response)
  //              {
  //                 next();
  //                 console.log(response);
  //              });
  //
  //         }
  //
  //
  //
  //           // var urlBase = $window.location.origin + $window.location.pathname;
  //           // $scope.success = true;
  //           // $scope.error = false;
  //           // $('.info-pop-header').text('Successfully Submited')
  //           // $('.info-pop-para').text('Please click dismiss')
  //           // $('.dismiss').removeClass('error-dismiss');
  //           // $('.dismiss').addClass('success-dismiss')
  //           // $('#articlePopBackground').fadeIn();
  //           //
  //           // $('.dismiss').on('click', function()
  //           // {
  //           //     $window.location.href = urlBase + '#!/my-articles';
  //           // });
  //
  //     });
  //
  // }
  // else if(title === '')
  // {
  //     $scope.error = true;
  //     $('.info-pop-header').text('Oooooops!')
  //     $('.info-pop-para').text('Please enter a title name');
  //     $('.dismiss').addClass('error-dismiss');
  //     $('#articlePopBackground').fadeIn();
  //
  //     $('.dismiss').on('click', function()
  //     {
  //
  //         $('#articlePopBackground').fadeOut();
  //
  //     });
  // }
  //
  // else if(category === null)
  // {
  //
  //     $scope.error = true;
  //     $('.info-pop-header').text('Oooooops!')
  //     $('.info-pop-para').text('Please select category');
  //     $('.dismiss').addClass('error-dismiss');
  //     $('#articlePopBackground').fadeIn();
  //
  //     $('.dismiss').on('click', function()
  //     {
  //
  //         $('#articlePopBackground').fadeOut();
  //
  //     });
  //
  // }
  //
  // if(summernote == null)
  // {
  //
  //     $scope.error = true;
  //     $('.info-pop-header').text('Oooooops!')
  //     $('.info-pop-para').text('Please write an article');
  //     $('.dismiss').addClass('error-dismiss');
  //     $('#articlePopBackground').fadeIn();
  //
  //     $('.dismiss').on('click', function()
  //     {
  //
  //         $('#articlePopBackground').fadeOut();
  //
  //     });
  //
  // }


   //  var urlBase = $window.location.origin + $window.location.pathname + '/article';
   //  var title = $scope.formParams.title;
   //  if(title === null)
   //  {
   //      title = 'null';
   //  }
   //  var category = $scope.formParams.category;
   //  var summernote = $('textarea#summernote').val();
   //
   //
   //  var file = $scope.file;
   //  var data = new FormData();
   //  data.append('file', file);
   //
   // $http({
   //
   //   url: urlBase,
   //   method: 'POST',
   //   data: data,
   //   params: {title: title, category: category, content: summernote},
   //   transformRequest: angular.identity,
   //   transformResponse: angular.identity,
   //   headers : {
   //   'Content-Type': undefined
   //   }
   //
   // }).then(function(response)
   // {
   //     var urlBase = $window.location.origin + $window.location.pathname;
   //     $scope.success = true;
   //     $scope.error = false;
   //     $('.info-pop-header').text('Successfully Submited')
   //     $('.info-pop-para').text('Please click dismiss')
   //     $('.dismiss').removeClass('error-dismiss');
   //     $('.dismiss').addClass('success-dismiss')
   //     $('#articlePopBackground').fadeIn();
   //
   //     $('.dismiss').on('click', function()
   //     {
   //         $window.location.href = urlBase + '#!/my-articles';
   //     });
   // });


    $scope.hideSuccessfulSubmit = function()
    {
        $state.go('my-articles');
        // $scope.hideSubmittedPopUp = true;

    };

}]);

app.controller('pendingArticlesController', ['PendingArticles', '$scope', '$state', 'localStorageService', '$timeout',
 function(PendingArticles, $scope, $state, localStorageService, $timeout)
{
    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $('#caPendingArticleLoader').fadeIn();
    $scope.showPendingArticles = false;
    $scope.fetchAllPendingArticle = function()
    {
        $scope.response = PendingArticles.query(function(response)
        {
            $timeout(function()
            {
                $('#caPendingArticleLoader').fadeOut();
                if(response.hasPendingArticles === 'OK')
                {
                    $scope.pendingArticle = response.pendingArticles;
                    $scope.showPendingArticles = true;
                }
                else
                {
                    $scope.showPendingArticles = true;
                }

            }, 2000);


        });
    }
    $scope.fetchAllPendingArticle();

    $scope.viewPendingArticle = function(articleId)
    {
        var id = articleId;
        $state.go('viewPendingArticle', {

            articleId: id

        });

    };

}]);

app.controller('viewPendingArticleController', ['OnePendingArticle', 'OnePendingServices', '$scope', '$state', '$stateParams', '$window', 'localStorageService', '$timeout',
 function(OnePendingArticle, OnePendingServices, $scope, $state, $stateParams, $window, localStorageService, $timeout)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.articleId = '';

    if($stateParams.articleId)
    {
        $scope.articleId = $stateParams.articleId;
    };
    $('#caViewPendingArticleLoader').fadeIn();
    $scope.viewPendingArticle = false;
    $scope.fetchOnePendingArticle = function()
    {
        var id = $scope.articleId;
        OnePendingArticle.getPendingArticle({article: id}, function(response)
        {
            $('#caViewPendingArticleLoader').fadeOut();
            $timeout(function()
            {
                $scope.viewPendingArticle = true;
                $scope.article = response.onePendingArticle;
                $('.innerPendingArticle').append($scope.article.content);

                $scope.empDetails = response.employeeDetails;
                $scope.userAvatar = response.userAvatar;
            }, 2000);


        });

    };
    $scope.fetchOnePendingArticle();

    $scope.approveArticle = function()
    {

        var id = $scope.articleId;
        OnePendingArticle.approveArticle({approve: id}, function(response)
        {
            if(response.articleApproved === 'OK')
            {
                console.log(response);
                $('#approvedArticle').fadeIn();
            }


        });

    };
    $scope.showRejectionForm = false;

    $scope.showRejectionForm = function()
    {

        $scope.showRejectionForm = true;

    };


    $scope.rejectArticle = function()
    {
        var id = $scope.articleId;
        var reason = $('#rejectionTextarea').val();
        var urlBase = $window.location.origin + $window.location.pathname + '/pending-articles/' + id;
        $scope.disable = true;
        OnePendingServices.saveRejection(id, reason, urlBase, function(response)
        {
            if(response.rejectionSaved === 'OK')
            {
                $('#rejectdArticle').fadeIn();
            }
            console.log(response)
        });

    };

    $scope.backToPendings = function()
    {

        $state.go('pendingArticles');

    };

}]);

app.controller('myDraftArticleController', ['DraftArticles', '$scope', '$state', 'localStorageService',
 function(DraftArticles, $scope, $state, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.fetchAllData = function()
    {

        $scope.responsed = DraftArticles.query(function(response)
        {
            if(response.responsedDraftArticle === 'OK')
            {
                $scope.draftArticles = response.draftArticles;
                $scope.numberOfDraftArticle = response.numberOfDraftArticle;
                console.log(response);
            }
            else if(response.responsedDraftArticle === 'NO_CONTENT')
            {
                console.log('No Content');
            }
        });
        console.log($scope.responsed);

    };
    $scope.fetchAllData();

    $scope.updateDraftArticle = function(draftArticleId)
    {

        console.log(draftArticleId + ' My draft Article');
        $state.go('article', {
            draftArticleId: draftArticleId
        });
    };


}]);

app.controller('newArticlesController', ['$scope', function($scope)
{



}]);

app.controller('myArticlesController', ['MyArticles', '$scope', '$state', 'localStorageService', '$timeout', '$rootScope',
 function(MyArticles, $scope, $state, localStorageService, $timeout, $rootScope)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.articles = [];
    $scope.pageSize = 6;
    $scope.currentPage = 1;

    $scope.myArticles = new MyArticles();
    $('#caMyArticleLoader').fadeIn();
    $scope.showMyArticles = false;
    $scope.fetchAllData = function()
    {

        $scope.responsed = MyArticles.query(function(response)
        {
            $('#caMyArticleLoader').fadeOut();
            $timeout(function()
            {
                $scope.showMyArticles = true;
                $scope.articles = response.articles;
                response.articles.forEach(function(data)
                {

                    data.date = new Date(data.dateCreated);

                });
            }, 2000);


        });

    };
    $scope.fetchAllData();

    $scope.oneArticle = function(articleId)
    {
        $scope.showMyArticles = false;
        $state.go('showArticle', {

            articleId: articleId

        })

    };

    $scope.article = function()
    {
        $scope.myArticles.$save(function(response)
        {
            var responseId = response.getDraftArticleId;
            $state.go('article', {

                draftArticleId: responseId

            });


        });

    };

    $rootScope.$on('$stateChangeStart',
    function(event, toState, toParams, fromState, fromParams){
        console.log(fromState);
    })
    $scope.articleId = '';
    $scope.showDeleteArticle = function(articleId)
    {
        $scope.articleId = articleId;
        $('#caDeleteArticle').fadeIn();
    };

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.inactiveArticle = function()
    {
        MyArticles.inactiveArticle({inactiveArticle: $scope.articleId}, function(response)
        {
            $scope.next('details2');
        });
    };

    $scope.closeDeletePop = function()
    {
        $scope.articleId = '';
        $timeout(function()
        {
            $scope.next('');
        }, 500);
        $('#caDeleteArticle').fadeOut();
    }

}]);

app.controller('myArticleController', ['MyArticles', '$scope', '$state', '$stateParams', 'localStorageService',
 function(MyArticles, $scope, $state, $stateParams, localStorageService)
{

      $scope.wideSearch = ''
      $scope.goSearch = function()
      {

          localStorageService.set('search', $scope.wideSearch);
          $state.go('search');

      };

      $scope.articleId = 0;

      if($stateParams.articleId)
      {
          $scope.articleId = $stateParams.articleId;
      }
      $scope.oneArticle = MyArticles.get({articleId : $scope.articleId });
      MyArticles.get({articleId : $scope.articleId }, function(response)
      {
          $('.innerArticle').append(response.article.content)
      });

      $scope.backToMyArticles = function()
      {
          $state.go('my-articles');
      };

}]);

app.controller('eventsController', ['EventResources', 'EventServices', '$scope', '$state', '$window', '$timeout', 'localStorageService',
 function(EventResources, EventServices, $scope, $state, $window, $timeout, localStorageService)
{
      $scope.wideSearch = ''
      $scope.goSearch = function()
      {

          localStorageService.set('search', $scope.wideSearch);
          $state.go('search');

      };

      $scope.carouselTimer = 1000;
      $scope.event = new EventResources();
      $scope.event.eventName = '';
      $scope.event.location = '';
      $scope.event.eventDate = '';
      $scope.event.details = '';

      $scope.formParams = {

          eventImg: []

      }
      $('#caEventLoader').fadeIn();
      $scope.events = [];
      $scope.eventPage = false;
      $scope.fetchAllEvents = function()
      {

          $scope.response = EventResources.query(function(response)
          {
              $scope.selectedEvent = response.selectedEvent;
              $scope.events = response.events;
              $('#caEventLoader').fadeOut();
              $timeout(function()
              {

                  $scope.eventPage = true;

              }, 2000);

          });

      };
      $scope.fetchAllEvents();

      $scope.selectOneEvent = function(eventId)
      {
          console.log(eventId);
          var id = eventId;
          var urlBase = $window.location.origin + $window.location.pathname + '/events';
          EventServices.selectEvent(id, urlBase, function(response)
          {
              if(response.isGoing === 'Going')
              {
                  $timeout(function()
                  {
                      $scope.isGoing = response.isGoing;
                  }, 500);
              }
              else if(response.isGoing === 'Not going')
              {
                  $timeout(function()
                  {
                      $scope.isGoing = response.isGoing;
                  }, 500);
              }
              $scope.oneEventDetailsBox = true;
              $scope.selectedEvent = response.selectedEvent;
              $scope.numOfGoing = response.numOfGoing;

          });
      };

      $scope.eventAttendance = function(eventId)
      {
          var id = eventId;
          var urlBase = $window.location.origin + $window.location.pathname + '/events';
          EventServices.eventAttendance(id, urlBase, function(response)
          {
              if(response.isGoing === 'Going')
              {
                  $scope.isGoing = response.isGoing;
              }
              else if(response.isGoing === 'Not going')
              {
                  $scope.isGoing = response.isGoing;
              }

          });
      };

      $(document).ready(function()
      {

        $("#companyEvents").owlCarousel({
          autoPlay: 6000,
        });

      });
    $scope.$on('$viewContentLoaded', function()
    {
        angular.element(document).ready(function()
        {

            $('#datetimepicker1').datetimepicker();
            $('#addEventButton').on('click', function()
            {

                $('#pop-wrapper').fadeIn();

            });

            $('.eventHeaderExit img').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

            $('.eventFormButtonBox').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

        });

    });

    $scope.showEventForm = function()
    {
        $('#eventPopUp').fadeIn();
    };

    $scope.closeEventForm = function()
    {
        $('#eventPopUp').fadeOut();
    };

    $scope.submit = function()
    {
        var dateTime = $('#eventDatePicker').val();
        $scope.event.eventDate = dateTime;
        $scope.event.base64 = $scope.formParams.eventImg.base64;
        $scope.event.fileType = $scope.formParams.eventImg.filetype;
        var details = $('#eventFormTextarea').val();
        $scope.event.details = details;
        if($scope.eventForm.$valid)
        {
            $scope.event.$save(function(response)
            {

                console.log(response);

            });
        }

    };

}]);

app.controller('allQuestionsController', ['AllQuestion', '$scope', '$filter', '$state', 'localStorageService', '$timeout',
 function(AllQuestion, $scope, $filter, $state, localStorageService, $timeout)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.questions = [];
    $scope.pageSize = 6;
    $scope.currentPage = 1;
    $scope.fetchAllData = function()
    {

        $scope.responsed = AllQuestion.query(function(results)
        {

            $scope.questions = results.questions;

        });

    };
    $scope.fetchAllData();

    $scope.goToQuestion = function(id)
    {
        $state.go('showNewsFeedQuestion', {

            id: id

        });
    };

    $scope.questionId = '';
    $scope.title = '';
    $scope.showDeleteQuestionBox = function(questionId, title)
    {
        $scope.questionId = questionId;
        $scope.title = title;
        $('#caDeleteQuestion').fadeIn();
    }

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.inactiveQuestion = function()
    {
        AllQuestion.inactiveQuestion({inactiveQuestion: $scope.questionId}, function(response)
        {
            console.log(response);
            $scope.fetchAllData();
            $timeout(function()
            {
                $scope.next('details2');
            }, 1000);
        });
    };

    $scope.closeDeleteQuestionBox = function()
    {

        $timeout(function()
        {
            $scope.next('');
        });
        $scope.questionId = '';
        $('#caDeleteQuestion').fadeOut();
    };


    function matchFirstChar(c, string)
    {
  		return (string.charAt(0) == c);
  	}

  	function removeFirstChar(string)
    {
  		  return string.slice(1);
  	}

  	function removeDash(label)
    {
    		if (matchFirstChar('-', label))
        {
    			return removeFirstChar(label);
    		}
    		return label;
  	}
  	function addDash(label)
    {
    		if (!matchFirstChar('-', label))
        {
    			return '-' + label;
    		}
    		return label;
  	}

    $scope.filterSort = function(element)
    {
    		if ($filter('filter')([element], $scope.filterTable).length > 0)
        {
    			   return 1;
    		}
    		return 2;
	  };

    $scope.singleSort = function(label)
    {
    		if ($scope.sortType == label)
        {
    			   $scope.sortReverse();
    		}
        else
        {
  			     $scope.sortType = label;
    		}
	  };

    $scope.sortReverse = function(set)
    {
    		set = set || false;
    		if (set || !matchFirstChar('-', $scope.sortType))
        {
    			   $scope.sortType = addDash($scope.sortType);
    		}
        else
        {
    			   $scope.sortType = removeDash($scope.sortType);
    		}
	  };

    $scope.singleSort = function(label)
    {
        if ($scope.sortType == label)
        {
            $scope.sortReverse();
        }
        else
        {
            $scope.sortType = label;
        }
    };

    $scope.sortDescend = function(label1, label2)
    {
          label2 = label2 || '';
          return ($scope.sortType == label1 || $scope.sortType == label2);
    };

    $scope.sortAscend = function(label1, label2)
    {
        label2 = label2 || '';
        return ($scope.sortType == ('-' + label1) || $scope.sortType == ('-' + label2));
    };

}])
.filter('startFrom', function(){

    return function(data, start){
        return data.slice(start);
    }

});

app.controller('myQuestionsController', ['MyQuestions', '$scope', '$timeout', '$state', 'localStorageService',
 function(MyQuestions, $scope, $timeout, $state, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.$on('$viewContentLoaded', function()
    {

        angular.element(document).ready(function()
        {

            $('.questionHeaderBox span').on('click', function()
            {

                $('#pop-wrapper').fadeIn();

            });

            $('.questionFormHeader img').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

            $('.questionCancelBox').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

            $('.qSuccFooter').on('click', function()
            {
                $('#pop-wrapper').fadeOut();
            });

        });

    });

    $scope.question = new MyQuestions();

    $scope.fetchAllData = function()
    {

        $scope.responsed = MyQuestions.query(function(response)
        {

            response.myQuestions.forEach(function(data)
            {

                data.date = new Date(data.dateCreated);

            });

        });

    };

    $scope.fetchAllData();

    $scope.submit = function()
    {

        if($scope.questionForm.$valid)
        {

            $scope.question.$save(function()
            {
                $scope.hideQuestionBox = true;
                $('#afterSubmitQuestion').fadeIn();
                $timeout(function()
                {

                    // $('#pop-wrapper').fadeOut();
                    $scope.question.title = '';
                    $scope.questionForm.$setPristine();
                    $scope.questionForm.$setUntouched();
                    $scope.hideAfterSub = true;
                    $scope.showQsuccessBox = true;
                }, 3000);

                $scope.fetchAllData();

            });

        }

    };


}]);

app.controller('peopleController', ['People', '$scope', '$state', '$timeout', 'localStorageService',
 function(People, $scope, $state, $timeout, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.showUserList = false;
    $scope.fetchAllUsers = function()
    {

        $scope.users = People.query(function(response)
        {
            $scope.employees = response.listOfEmployee;
            $scope.selectedEmpDetails = response.selectedEmpDetails;
            $('#caPeopleLoader').fadeOut();
            $timeout(function()
            {

                $scope.showUserList = true;

            }, 1000);
            console.log(response);
        });

    };
    $scope.fetchAllUsers();

    $scope.getEmployeeDetails = function(empDetailsId)
    {
        $('#empDetailsBox').fadeOut();
        $('#oneEmpLoaderBox').fadeIn()
        var id = empDetailsId;
        $scope.oneEmployee = People.get({empDetailsId: id}, function(response)
        {
            $('#oneEmpLoaderBox').fadeOut()
            $timeout(function()
            {
                $scope.selectedEmpDetails = response.selectedEmpDetails;
                $('#empDetailsBox').fadeIn();
            }, 600);

        });
    }


}]);

app.controller('myProfileController', ['MyProfile', 'ProfilePicUpload', '$scope', '$state', '$window', '$http', '$timeout', '$rootScope', 'localStorageService',
 function(MyProfile, ProfilePicUpload, $scope, $state, $window, $http, $timeout, $rootScope, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.$on('$viewContentLoaded', function()
    {

        angular.element(document).ready(function()
        {

            $('.leftBannerDescription').hover(function()
            {
                $('.userProfEditCon').toggleClass('editContainerEvent');
            });

            $('.rightBanner').hover(function()
            {
                $('.userProfEditCon2').toggleClass('editContainerEvent');
            });

            $('.aboutContainer').hover(function()
            {
                $('.editTextBox span').toggleClass('editTextBoxEvent')
            })

        });

    });


    $scope.myProfile = new MyProfile();
    $scope.formParams = {

        userAvatar: []

    };

    function executeAvatar()
    {
    }
    $('#caMyProfileLoader').fadeIn();
    $scope.showMyProfile = false;
    $scope.fetchMyDetails = function()
    {

        $scope.response = MyProfile.query(function(response)
        {

            $scope.userDetails = response.userDetails;
            $scope.empDetails = response.empDetails;
            if(response.hasAnAvatar === 'OK')
            {
                $timeout(function()
                {
                    $('#caMyProfileLoader').fadeOut();
                    $scope.showMyProfile = true;
                    $scope.avatar = response.employeeAvatar;
                    $scope.formParams.userAvatar.filetype = $scope.avatar.fileType;
                    $scope.formParams.userAvatar.base64 = $scope.avatar.base64

                }, 2000);
                ;
            }
            else
            {

                $('#caMyProfileLoader').fadeOut();
                $scope.showMyProfile = true;

            }


        });

    };

    $scope.fetchMyDetails();


    $scope.uploadProfilePic = function()
    {
        $scope.logLoading = true;
        $scope.myProfile.fileName = $scope.formParams.userAvatar.filename;
        $scope.myProfile.fileType = $scope.formParams.userAvatar.filetype;
        $scope.myProfile.base64 = $scope.formParams.userAvatar.base64;

        $timeout(function()
        {
            $scope.myProfile.$save(function(response)
            {
                localStorageService.set('avatar', response.userAvatar);

                $rootScope.$emit('UpdatedAvatar', {

                    updatedAvatar: true

                });

                alert('Seccussfully uploaded');
                $('#caEditAvatar').fadeOut();
                $scope.logLoading = false;
                $scope.fetchMyDetails();
            });
        }, 3000);

        // var urlBase = $window.location.origin + $window.location.pathname + '/my-profile';
        //
        // $http({
        //  method: 'POST',
        //  url: urlBase,
        //  data: $scope.formParams,
        //  headers: {
        //      'Content-Type': 'application/json' }
        // });

    };

    $scope.showAvatarPop = function()
    {

        $('#caEditAvatar').fadeIn();

    };

    $scope.showUsernamePop = function()
    {
        $('#caEditUsernamePop').fadeIn();
    };

    $scope.showPasswordPop = function()
    {
        $('#caEditPasswordPop').fadeIn();
    };

    $scope.showPositionPop = function()
    {
        $('#caEditPositionPop').fadeIn();
    };

    $scope.showEmailPop = function()
    {
        $('#caEditEmailPop').fadeIn();
    };

    $scope.showContactPop = function()
    {
        $('#caEditContactNumPop').fadeIn();
    };

    $scope.showGenderPop = function()
    {
        $('#caEditGenderPop').fadeIn();
    };

    $scope.cancelEdit = function()
    {
        $('#caEditUsernamePop').fadeOut();
        $('#caEditPasswordPop').fadeOut();
        $('#caEditPositionPop').fadeOut();
        $('#caEditPositionPop').fadeOut();
        $('#caEditEmailPop').fadeOut();
        $('#caEditContactNumPop').fadeOut();
        $('#caEditGenderPop').fadeOut();
        $('#caEditAvatar').fadeOut();
    };

    $scope.password = '';
    $scope.updatePassword = function()
    {

        MyProfile.updatePassword({password: $scope.password}, function(response)
        {
            console.log(response);
            alert('Password Successfully Updated');
            $('#caEditUsernamePop').fadeOut();
            $scope.fetchMyDetails();
        });
    };

    $scope.position = '';
    $scope.updatePosition = function()
    {
        MyProfile.updatePosition({position: $scope.position}, function(response)
        {
            console.log(response);
            alert('Position Successfully Updated');
            $('#caEditPositionPop').fadeOut();
            $scope.fetchMyDetails();
        });
    };

    $scope.email = '';
    $scope.updateEmail = function()
    {
        MyProfile.updateEmail({email: $scope.email}, function(response)
        {
            console.log(response);
            alert('Email Successfully Updated');
            $('#caEditEmailPop').fadeOut();
            $scope.fetchMyDetails();
        });
    };

    $scope.contactNumber = '';
    $scope.updateContactNumber = function()
    {
        MyProfile.updateContactNumber({contactNumber: $scope.contactNumber}, function(response)
        {
            console.log(response);
            alert('Contact Number Successfully Updated');
            $('#caEditContactNumPop').fadeOut();
            $scope.fetchMyDetails();
        });
    };

    $scope.gender = '';
    $scope.updateGender = function()
    {
        var gender = '';
        if($scope.gender === '')
        {
            gender = 'Others'
            console.log(gender);
        }
        else
        {
            gender = $scope.gender
            console.log(gender);
        }
        MyProfile.updateGender({gender: gender}, function(response)
        {
            console.log(response);
            alert('Gender Successfully Updated');
            $('#caEditGenderPop').fadeOut();
            $scope.fetchMyDetails();
        });
    };


    $scope.profilePicture = new ProfilePicUpload();

    $scope.fetcgProfilePicUpload = function()
    {

        $scope.picResponsed = ProfilePicUpload.query();

    };
    $scope.fetcgProfilePicUpload;

    $scope.imageSrc = "";

    $scope.$on("fileProgress", function(e, progress)
    {

        $scope.progress = progress.loaded / progress.total;
        console.log($scope.imageSrc);
    });
    $scope.changePicture = function()
    {

    };

    $scope.showFirstForm = function()
    {
        $scope.firstFormActive = true;
        $scope.secondFormActive = false;
        $scope.thirdFormActive = false;
    };

    $scope.hideFirstForm = function()
    {
        $scope.firstFormActive = false;
    };

    $scope.showSecondForm = function()
    {
        $scope.secondFormActive = true;
        $scope.firstFormActive = false;
        $scope.thirdFormActive = false;
    };

    $scope.hideSecondForm = function()
    {
        $scope.secondFormActive = false;
    };

    $scope.showThirdForm = function()
    {
        $scope.thirdFormActive = true;
        $scope.firstFormActive = false;
        $scope.secondFormActive = false;
    };

    $scope.hideThirdForm = function()
    {
        $scope.thirdFormActive = false;
    };

}]);

app.directive("ngFileSelect", function(fileReader, $timeout)
{
    return {
      scope: {
        ngModel: '='
      },
      link: function($scope, el)
       {
        function getFile(file)
        {
          fileReader.readAsDataUrl(file, $scope)
            .then(function(result)
            {
              $timeout(function()
              {
                  $scope.ngModel = result;
                  // console.log($scope.ngModel);
              });
            });
        }

        el.bind("change", function(e)
        {
            var file = (e.srcElement || e.target).files[0];
            getFile(file);
        });
      }
    };
  });

app.factory("fileReader", function($q, $log)
{
  var onLoad = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.resolve(reader.result);
      });
    };
  };

  var onError = function(reader, deferred, scope) {
    return function() {
      scope.$apply(function() {
        deferred.reject(reader.result);
      });
    };
  };

  var onProgress = function(reader, scope) {
    return function(event) {
      scope.$broadcast("fileProgress", {
        total: event.total,
        loaded: event.loaded
      });
    };
  };

  var getReader = function(deferred, scope) {
    var reader = new FileReader();
    reader.onload = onLoad(reader, deferred, scope);
    reader.onerror = onError(reader, deferred, scope);
    reader.onprogress = onProgress(reader, scope);
    return reader;
  };

  var readAsDataURL = function(file, scope) {
    var deferred = $q.defer();

    var reader = getReader(deferred, scope);
    reader.readAsDataURL(file);
    console.log(reader);
    return deferred.promise;
  };
  return {
    readAsDataUrl: readAsDataURL
  };
});

app.controller('artcleCategoriesController', ['Category', 'CategoryServices', '$scope', '$state', 'localStorageService', '$window', '$timeout',
 function(Category, CategoryServices, $scope, $state, localStorageService, $window, $timeout)
{
    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.$on('$viewContentLoaded', function()
    {

        angular.element(document).ready(function()
        {

            $('#categoryNameBox').click(function(e)
            {

                $('#pop-wrapper').fadeIn();

            });

            $('.editCategoryExit img').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

            $('.editCatCancelButton').on('click', function()
            {

                $('#pop-wrapper').fadeOut();

            });

        });

    });

    $scope.categoryResources = new Category();

    $scope.showAddCategory = function()
    {
        $('#caAddCategory').fadeIn();
    };



    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;
        if($scope.details === 'details3')
        {
            $scope.hideCloseButton = true;
        }

    };
    $scope.back = function (details)
    {

      $scope.direction = 0;
      $scope.details = details;

    };

    $scope.listAllCategories = function()
    {
        $scope.response = Category.query(function(response)
        {
            if(response.hasAnCategories === 'OK')
            {
                $scope.categories = response.categories;
                $scope.numberOfCategory = response.numberOfCategory;
            }
        });
    };

    $scope.listAllCategories();

    $scope.saveCategory = function()
    {
        if($scope.categoriesForm.$valid)
        {

            $scope.categoryResources.$save(function(result)
            {
                $scope.clear();
                $scope.listAllCategories();
                $('#caAddCategory').fadeOut();
                alert('Successfully added');
            }).then(function()
            {

                $scope.categoryResources = new Category();

            });

        }
    };

    $scope.getCategory = function(id)
    {
        $scope.oneCategory = Category.get({categoryId: id});

    };

    $scope.oldCategory = '';
    $scope.showEditCategory = function(categoryName)
    {
        $scope.oldCategory = categoryName;
        $('#caEditCategory').fadeIn()
    };

    $scope.categoryParams = {
        category:
        {
            categoryName: ''
        }
    };

    $scope.disable = true;

    $scope.updateCategory = function()
    {
        var newCategory = $scope.categoryParams.category.categoryName;
        var oldCategory = $scope.oldCategory;
        var urlBase = $window.location.origin + $window.location.pathname + '/categories';
        CategoryServices.updateCategory(newCategory, oldCategory, urlBase, function(response)
        {
            $scope.editCategoryForm.$setPristine();
            $scope.editCategoryForm.$setUntouched();
            $timeout(function()
            {
                $scope.categoryParams.category.categoryName = '';
                $scope.next('details4');
                $scope.listAllCategories();

            }, 2000);

        });

    };

    $scope.closeCategoryBox = function()
    {
        $timeout(function()
        {
            $scope.next('');
            $scope.hideCloseButton = false;
        }, 1000);
        $('#caAddCategory').fadeOut();
        $('#caEditCategory').fadeOut()
    };

    $scope.categoryParams = {
        category:
        {
            deleteCategoryName: ''
        }
    };

    $scope.showDeleteCategoryBox = function(categoryName)
    {
        $scope.categoryParams.category.deleteCategoryName = categoryName;
        $('#caDeleteCategory').fadeIn();
    };

    $scope.nextDeleteLoader = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;
        if($scope.details === 'details2')
        {
            $scope.hideCloseButton = true;
        }

    };

    $scope.deleteCategory = function()
    {
        var categoryName = $scope.categoryParams.category.deleteCategoryName
        var urlBase = $window.location.origin + $window.location.pathname + '/categories';
        $scope.hideCloseButton = true;
        CategoryServices.deleteCategory(categoryName, urlBase, function(response)
        {
            $timeout(function()
            {

                $scope.next('details4');
                $scope.listAllCategories();

            }, 2000);


        });
    };

    $scope.closeDeleteCategoryBox = function()
    {
        $('#caDeleteCategory').fadeOut();
    };


    $scope.clear = function()
    {
        $scope.categoryResources = {};
        $scope.categoryParams.category.categoryName = '';
        $scope.categoriesForm.$setPristine();
        $scope.categoriesForm.$setUntouched();
    };

    $scope.clearDeleteData = function()
    {
        $timeout(function()
        {
            $scope.hideCloseButton = false;
            $scope.next('');

        }, 1000);

    };

    $scope.goToManageCategories = function()
    {
          $state.go('manageCategories');
    };

}]);

app.controller('manageCategoriesController', ['ManageCategoryResources', 'ManageCategoryServices', '$scope', '$window', '$state', '$filter',
 function(ManageCategoryResources, ManageCategoryServices, $scope, $window, $state, $filter)
{

    $scope.articles = [];
    $scope.pageSize = 10;
    $scope.currentPage = 1;

    $scope.fetchAllData = function()
    {
        ManageCategoryResources.query(function(response)
        {

            if(response.hasCategories)
            {
                $scope.articleCategorys = response.articleCategorys;
                $scope.moveToArticle = response.moveToArticle;
            }
            if(response.hasArticles === 'OK')
            {
                $scope.articles = response.articles;

                $scope.articles.forEach(function(data)
                {

                    data.date = new Date(data.dateCreated);

                });

            }
            console.log(response);
        });
    };

    $scope.fetchAllData();

    $scope.categorySelection = 'All Article';
    $scope.articleSelection = [];

    $scope.$watch('categorySelection', function(newValue, oldValue)
    {

        if(newValue != oldValue)
        {
            $scope.articleSelection = [];
        }

    });


    $scope.toggleSelection = function(articleId)
    {
        var index = $scope.articleSelection.indexOf(articleId);

        if(index > -1)
        {
            $scope.articleSelection.splice(index, 1);
        }
        else
        {
            $scope.articleSelection.push(articleId);
        }


    };

    $scope.moveToCategory = ''
    $scope.moveArticle = function()
    {
        var articles = $scope.articleSelection;
        var moveTo = $scope.moveToCategory;
        var urlBase = $window.location.origin + $window.location.pathname + '/manage-categories';
        ManageCategoryServices.updateCategory(articles, moveTo, urlBase, function(response)
        {
            $scope.sizeOfArticles = articles.length;
            $('#MgtCatPopBackground').fadeIn();
            console.log(response);

        });
    };

    $scope.closeSuccessUpdateCat = function()
    {
        $scope.articleSelection = [];
        $scope.fetchAllData();
        $('#MgtCatPopBackground').fadeOut();

    };

    $scope.backToCategoryList = function()
    {

        $state.go('articleCategories');

    };



    function matchFirstChar(c, string)
    {
  		return (string.charAt(0) == c);
  	}

  	function removeFirstChar(string)
    {
  		  return string.slice(1);
  	}

  	function removeDash(label)
    {
    		if (matchFirstChar('-', label))
        {
    			return removeFirstChar(label);
    		}
    		return label;
  	}
  	function addDash(label)
    {
    		if (!matchFirstChar('-', label))
        {
    			return '-' + label;
    		}
    		return label;
  	}

    $scope.filterSort = function(element)
    {
    		if ($filter('filter')([element], $scope.filterTable).length > 0)
        {
    			   return 1;
    		}
    		return 2;
	  };

    $scope.singleSort = function(label)
    {
    		if ($scope.sortType == label)
        {
    			   $scope.sortReverse();
    		}
        else
        {
  			     $scope.sortType = label;
    		}
	  };

    $scope.sortReverse = function(set)
    {
    		set = set || false;
    		if (set || !matchFirstChar('-', $scope.sortType))
        {
    			   $scope.sortType = addDash($scope.sortType);
    		}
        else
        {
    			   $scope.sortType = removeDash($scope.sortType);
    		}
	  };

    $scope.singleSort = function(label)
    {
        if ($scope.sortType == label)
        {
            $scope.sortReverse();
        }
        else
        {
            $scope.sortType = label;
        }
    };

    $scope.sortDescend = function(label1, label2)
    {
          label2 = label2 || '';
          return ($scope.sortType == label1 || $scope.sortType == label2);
    };

    $scope.sortAscend = function(label1, label2)
    {
        label2 = label2 || '';
        return ($scope.sortType == ('-' + label1) || $scope.sortType == ('-' + label2));
    };


}]).filter('startFrom', function(){

    return function(data, start){
        return data.slice(start);
    }

});

app.controller('allNewsController', ['NewsResources', '$scope', '$state', '$timeout', 'localStorageService',
 function(NewsResources, $scope, $state, $timeout, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $('#caAllNewsLoader').fadeIn();
    $scope.showAllNews = false;

    $scope.news = [];
    $scope.pageSize = 5;
    $scope.currentPage = 1;
    $scope.fetchAllNews = function()
    {
        $scope.response = NewsResources.query(function(response)
        {

            if(response.hasNews === 'OK')
            {
                $scope.news = response.newsRest;
                $('#caAllNewsLoader').fadeOut();
                $timeout(function()
                {

                    $scope.showAllNews = true;

                }, 1000);

                $scope.news.forEach(function(data)
                {
                    data.date = new Date(data.dateCreated);

                });


            }
            else if(response.hasNews === 'NO_CONTENT')
            {
                $('#caAllNewsLoader').fadeOut();
                $timeout(function()
                {

                    $scope.showAllNews = true;

                }, 1000);
            }

        });
    };
    $scope.fetchAllNews();

    $scope.draftNews = new NewsResources();

    $scope.createNews = function()
    {
        $('#allNews').fadeOut();

        $timeout(function()
        {
            $('#caCreatingDraftNewsLoader').fadeIn();
        }, 800);

        $scope.draftNews.$save(function(response)
        {
            $timeout(function()
            {

                var id = response.draftNewsSaved;
                $state.go('createNews', {

                    draftNewsId: id

                });

            }, 3000);

        });
    };

    $scope.oneNews = function(newsId)
    {

        $state.go('showNews', {

            newsId: newsId

        });

    };
    $scope.newsId = '';
    $scope.showDeleteNewsPop = function(newsId)
    {
        $scope.newsId = newsId;
        $('#caDeleteNews').fadeIn();
    };

    $scope.next = function(details)
    {

        $scope.direction = 1;
        $scope.details = details;

    };

    $scope.inactiveNews = function()
    {

        NewsResources.inactiveNews({inactiveNews: $scope.newsId}, function(response)
        {
            $scope.fetchAllNews();
            $scope.next('details2');
        });

    };

    $scope.closeDeleteNewsPop = function()
    {
        $scope.newsId = '';
        $('#caDeleteNews').fadeOut();
        $timeout(function()
        {
            $scope.next('');
        }, 1000);
    };

}]);

app.controller('oneNewsController', ['OneNewsServices', '$scope', '$state', '$stateParams', '$window', '$timeout', 'localStorageService',
 function(OneNewsServices, $scope, $state, $stateParams, $window, $timeout, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.newsId = '';

    if($stateParams.newsId)
    {
        $scope.newsId = $stateParams.newsId;
    }
    $('#caOneNewsLoader').fadeIn();
    $scope.showOneNews = false;
    $scope.fetchOneNews = function()
    {
        var id = $scope.newsId;
        var urlBase = $window.location.origin + $window.location.pathname + '/news/company-news/' + id;
        OneNewsServices.getOneNews(id, urlBase, function(response)
        {
            $scope.oneNews = response.news;
            $('.newsArticle').append($scope.oneNews.newsContent);
            $('#caOneNewsLoader').fadeOut();

            $timeout(function()
            {
                $scope.showOneNews = true;
            }, 1000);

        });

    };
    $scope.fetchOneNews();

    $scope.backToAllNews = function()
    {
        $scope.showOneNews = false;

        $timeout(function()
        {

            $state.go('allNews');

        }, 600);

    };

}]);

app.controller('createNewsController', ['NewsCreateResources', 'NewsServices', '$scope', '$state', '$stateParams', '$interval', '$window', 'localStorageService',
 function(NewsCreateResources, NewsServices, $scope, $state, $stateParams, $interval, $window, localStorageService)
{
    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.dNewsId = '';

    if($stateParams.draftNewsId)
    {
        $scope.dNewsId = $stateParams.draftNewsId;
    }

    $scope.draftNews = new NewsCreateResources();
    $scope.title = '';




    $scope.$on('$viewContentLoaded', function()
    {

        angular.element(document).ready(function()
        {

            $('#summernoteNews').summernote();

        });

    });

    $scope.formParams = {

      frontImage: []

    }


    // watch unique input then save if value has change
    var watchSummernote = $interval(function()
    {

        $scope.summernoteValue = $('textarea#summernoteNews').val();

    }, 1000);

    var watchFrontImage = $interval(function()
    {

        $scope.frontImage = $scope.formParams.frontImage;

    }, 1000);

    $scope.updateNews = function()
    {
        $scope.$watchGroup(['title', 'frontImage', 'summernoteValue'], function(newValue, oldValue)
        {
            $scope.draftNews.draftNewsId = $scope.dNewsId;
            console.log($scope.draftNews.draftNewsId);
            if(newValue != oldValue)
            {
                $scope.draftNews.draftNewsTitle = $scope.title;
                $scope.draftNews.draftBase64 = $scope.formParams.frontImage.base64;
                $scope.draftNews.draftFileType = $scope.formParams.frontImage.filetype;
                $scope.draftNews.draftNewsContent = $scope.summernoteValue;
                NewsCreateResources.updateDraftNews({draftNewsId: $scope.draftNews.draftNewsId}, $scope.draftNews, function(response)
                {
                    console.log(response);
                });

            }
        });

    };

    $scope.updateNews();

    $scope.populateNewsWriting = function()
    {
        var id = $scope.dNewsId;
        var urlBase = $window.location.origin + $window.location.pathname + '/news/' + id;
        NewsServices.getCurrentDraftNews(id, urlBase, function(response)
        {
            console.log(response.newsCreated.draftNewsTitle);
            $scope.setTitle = function()
            {
                $scope.title = response.newsCreated.draftNewsTitle;
            };
            $scope.setTitle();
            $scope.setContent = function()
            {
                $('#summernoteNews').summernote('code', response.newsCreated.draftNewsContent);
            }
            $scope.setContent();
            $scope.setFrontImage = function()
            {
              if(response.newsCreated.base64 === null)
              {
                  $scope.formParams = {

                    frontImage: []

                  }
              }
              else
              {
                  $scope.formParams = {

                    frontImage: {

                        base64: response.newsCreated.base64,
                        filetype: response.newsCreated.fileType

                    }

                  }
              }


            };
            $scope.setFrontImage();
            console.log(response);
        });

    };
    $scope.populateNewsWriting();


    function next()
    {

      $scope.article = 'article2';

    };

    $scope.submit = function()
    {
        var id = $scope.draftNews.draftNewsId;
        var urlBase = $window.location.origin + $window.location.pathname + '/news/' + id;

        $('#savingNews').fadeIn();


        NewsServices.saveNews(id, urlBase, function(response)
        {
            console.log(response);
            if(response.data.hasSaved === 'OK')
            {
                next();
            }
        });

    };



    $scope.redirectToNews = function()
    {

        $state.go('allNews');

    };

}]);

app.controller('draftNewsController', ['DraftNewsResources', '$scope', '$state', 'localStorageService',
 function(DraftNewsResources, $scope, $state, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.fetchAllDraftNews = function()
    {

        $scope.response = DraftNewsResources.query(function(response)
        {
            $scope.numOfDraftNews = response.numberOfDraftNews;
            if(response.hasDraftNews === 'OK')
            {
                $scope.draftNews = response.draftNewses;
                console.log($scope.draftNews);
            }

        });

    }
    $scope.fetchAllDraftNews();

    $scope.updateDraftNews = function(draftNewsId)
    {

        $state.go('createNews', {
            draftNewsId: draftNewsId
        });

    };

}]);

app.controller('myCampaignsController', ['$scope', function($scope)
{

}]);



app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider)
{
    $urlRouterProvider.otherwise('/dashboard');
    $stateProvider.
    state('newsFeed', {

        url: '/news-feed',

        views: {
            'newsFeed': {

                templateUrl: 'newsFeed',
                controller: 'newsFeedController'

            }
        }

    }).
    state('search', {

        url: '/search',

        views: {
            'search': {

                templateUrl: 'search',
                controller: 'searchController'

            }
        }

    }).
    state('dashboard', {

        url: '/dashboard',

        views: {
            'dashboard': {

                templateUrl: 'dashboard',
                controller: 'dashboardController'

            }
        }

    }).
    state('showNewsFeedArticle', {

        url: '/news-feed/article/{id}',

        views: {
            'showNewsFeedArticle': {

                templateUrl: 'showNewsFeedArticle',
                controller: 'newsFeedArticleController'

            }
        }

    }).
    state('showNewsFeedQuestion', {

        url: '/news-feed/question/{id}',

        views: {
            'showNewsFeedQuestion': {

                templateUrl: 'showNewsFeedQuestion',
                controller: 'newsFeedQuestionController'

            }
        }

    }).
    state('allNews', {

        url: '/news',

        views: {
            'allNews': {

                templateUrl: 'allNews',
                controller: 'allNewsController'

            }
        }

    }).
    state('showNews', {

        url: '/news/company-news/{newsId}',

        views: {
            'showNews': {

                templateUrl: 'showNews',
                controller: 'oneNewsController'

            }
        }

    }).
    state('createNews', {

        url: '/news/{draftNewsId}',

        views: {
            'createNews': {

                templateUrl: 'createNews',
                controller: 'createNewsController'

            }
        }

    }).
    state('draftNews', {

        url: '/draft-news',

        views: {
            'draftNews': {

                templateUrl: 'draftNews',
                controller: 'draftNewsController'

            }
        }

    }).
    state('all-articles', {

        url: '/all-article',

        views: {
            'allArticles': {

                templateUrl: 'allArticles',
                controller: 'allArticleController'

            }
        }

    }).
    state('new-articles', {

        url: '/new-articles',

        views: {
            'new-articles': {

                templateUrl: 'new-articles',
                controller: 'newArticlesController'

            }
        }

    }).
    state('my-articles', {

        url: '/my-articles',

        views: {
            'my-articles': {

                templateUrl: 'my-articles',
                controller: 'myArticlesController'

            }
        }

    }).
    state('showArticle', {

        url: '/my-articles/{articleId}',

        views: {
            'showArticle': {

                templateUrl: 'showArticle',
                controller: 'myArticleController'

            }
        }

    }).
    state('article', {

        url: '/article/{draftArticleId}',

        views: {
            'article': {

                templateUrl: 'article',
                controller: 'articleController'

            }
        }

    }).
    state('pendingArticles', {

        url: '/pending-articles',

        views: {
            'pendingArticles': {

                templateUrl: 'pendingArticles',
                controller: 'pendingArticlesController'

            }
        }

    }).
    state('viewPendingArticle', {

        url: '/pending-articles/{articleId}',

        views: {
            'viewPendingArticle': {

                templateUrl: 'viewPendingArticle',
                controller: 'viewPendingArticleController'

            }
        }

    }).
    state('myDraftArticles', {

        url: '/my-draft-articles',

        views: {
            'myDraftArticles': {

                templateUrl: 'myDraftArticles',
                controller: 'myDraftArticleController'

            }
        }

    }).
    state('events', {

        url: '/events',

        views: {
            'events': {

                templateUrl: 'events',
                controller: 'eventsController'

            }
        }

    }).
    state('questions', {

        url: '/all-question',

        views: {
            'all-question': {

                templateUrl: 'questions',
                controller: 'allQuestionsController'

            }
        }

    }).
    state('new-questions', {

        url: '/questions/new-questions',

        views: {
            'new-questions': {

                templateUrl: 'new-questions',
                controller: 'newQuestionsController'

            }
        }

    }).
    state('my-questions', {

        url: '/questions/my-questions',

        views: {
            'my-questions': {

                templateUrl: 'my-questions',
                controller: 'myQuestionsController'

            }
        }

    }).
    state('people', {

        url: '/people',

        views: {

            'people': {

                templateUrl: 'people',
                controller: 'peopleController'

            }
        }

    }).
    state('my-profile', {

        url: '/my-profile',

        views: {

            'my-profile': {

                templateUrl: 'my-profile',
                controller: 'myProfileController'

            }
        }

    }).state('articleCategories', {

        url: '/categories',

        views: {


            'articleCategories': {

                templateUrl: 'articleCategories',
                controller: 'artcleCategoriesController'

            }

        }

    }).state('manageCategories', {

        url: '/manage-categories',

        views: {


            'manageCategories': {

                templateUrl: 'manageCategories',
                controller: 'manageCategoriesController'

            }

        }

    });

}]);
