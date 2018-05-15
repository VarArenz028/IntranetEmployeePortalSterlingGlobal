var app = angular.module('sterling.controllers', ['ui.router', 'ngAnimate', 'ngCookies', 'ngMessages', 'chart.js', 'naif.base64', 'ui.bootstrap', 'LocalStorageModule', 'ngStorage']);

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
    // $scope.showAvatar = false;
    //
    // $scope.init = function()
    // {
    //     console.log('Excuting here');
    //     $scope.avatarFileType = $scope.avatar.fileType;
    //     $scope.avatarBase64 = $scope.avatar.base64;
    //     $('#caSideNavLoader').fadeOut();
    //     $scope.showAvatar = true;
    // };
    //
    // $scope.avatar = localStorageService.get('avatar');
    //
    // $rootScope.$on('UpdatedAvatar', function(evt, args)
    // {
    //     if(args.updatedAvatar == true)
    //     {
    //         $scope.avatar = localStorageService.get('avatar');
    //         $scope.avatarFileType = $scope.avatar.fileType;
    //         $scope.avatarBase64 = $scope.avatar.base64;
    //     }
    //
    // });
    //
    // $scope.$watchGroup(['avatar.fileType', 'avatar.base64', 'avatar.fileName'], function(newValue, oldValue)
    // {
    //     var nValue = newValue[2];
    //     var oValue = oldValue[2];
    //     $scope.avatar = localStorageService.get('avatar');
    //     if(nValue !== oValue)
    //     {
    //         console.log('Avatar has been changed');
    //         $scope.avatarFileType = $scope.avatar.fileType;
    //         $scope.avatarBase64 = $scope.avatar.base64;
    //     }
    //     if($scope.avatar == null)
    //     {
    //         $scope.defaultAvatar = true;
    //     }
    //     console.log('In watch');
    // }, true);


    var cookie = $cookies.getObject('globals');
    var campaign = $cookies.getObject('campGlobals');

    $scope.username = cookie.currentUser.username;
    $scope.campaign = campaign.currentCampaign.campaignName;

    // $transitions.onStart({}, ($scope, $state)=>{
    //       console.log($state.current.name);
    //        $log.log('In start')
    //    });
    //    $transitions.onFinish({}, ()=>{
    //        $log.log('In finish')
    //    });

    $scope.newsFeed = function()
    {

        $state.go('newsFeed');

    };

    $scope.dashboard = function()
    {

        $state.go('dashboard');

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

    $scope.myDraftArticles = function()
    {
        $state.go('myDraftArticles');
    }

    $scope.allNews = function()
    {
        $state.go('allNews');
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
     var replace = $window.location.pathname.replace('agent', '') ;

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
    $('#aNewsFeedLoader').fadeIn();
    $scope.fetchAllNewsFeed = function()
    {

        $scope.responsed = NewsFeed.query(function(response)
        {
          $('#aNewsFeedLoader').fadeOut();
          $timeout(function()
          {
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

                $scope.showNewsFeed = true;



                if(response.hasPinToTopArticles === 'OK')
                {
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

                    $scope.showNewsFeed = true;
                }
                else if(response.hasPinToTopArticles === 'NO_CONTENT')
                {

                }

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


}]);

app.controller('dashboardController', ['Dashboard', '$scope', '$timeout', 'localStorageService',
 function(Dashboard, $scope, $timeout, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.categoryLabels = [];
    $scope.categoryDatas = [];
    $scope.fetchAllData = function()
    {

        $scope.response = Dashboard.query(function(response)
        {
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
                console.log('No Contributers');
            }

            if(response.hasMostView === 'OK')
            {
                $scope.articlesMostView = response.mostView;
            }
            else if(response.hasMostView === 'NO_CONTENT')
            {
                console.log('Articles not seen yet');
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
        });


        $timeout(function()
        {
            $('#caDashboardLoader').fadeOut();
            $scope.showDashboard = true;
        }, 1000);
    };
    $scope.fetchAllData();

    $scope.showAllAuthor = false;

    $scope.showAllAuthorTable = function()
    {
        $scope.showAllAuthor = true;
    };

    $scope.hideAllAuthorTable = function()
    {
        $scope.showAllAuthor = false;
    };

    $scope.goToArticleSelected = function(id)
    {
        $state.go('showNewsFeedArticle', {

            id: id,

        });
    };

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

}]);

app.controller('newsFeedArticleController', ['NewsFeedArticle', '$scope', '$state', '$stateParams', '$http', '$window', '$timeout', '$rootScope', 'localStorageService',
 function(NewsFeedArticle, $scope, $state, $stateParams, $http, $window, $timeout, $rootScope, localStorageService)
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

        });

    };
    $scope.fetchAllData();
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
            // var downloadPath = urlBase + '?fileId=' + 1;
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
            $scope.fetchAllData();
        });

    };

    $scope.backToNewsFeed = function()
    {
        $state.go('newsFeed');
    };

}]);

app.controller('searchController', ['SearchServices', '$scope', '$state', '$window', 'localStorageService', '$timeout',
 function(SearchServices, $scope, $state, $window, localStorageService, $timeout)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

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
            $timeout(function()
            {

                $scope.showSearchedResult = true;

            }, 2000);
        });
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
            console.log($scope.answers);

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

    $scope.$on('$viewContentLoaded', function() {

        angular.element(document).ready(function()
        {

            $('#answerSummernote').summernote();

        });

    });

    $('#submitAnswer').on('click', function()
    {

        var content = $("#answerQuestionForm").find("textarea[name = 'summernote']").val();
        $scope.answer = content;

    });

    $scope.submitAnswer = function()
    {

      var questionId = $scope.questionId;
      var urlBase = $window.location.origin + $window.location.pathname + '/news-feed/question/' +  $scope.questionId + '/';

      AnswerQuestionServices.submitAnswer($scope.answer, questionId, function(response)
      {
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

app.controller('allArticleController', ['AllArticle', '$scope', '$filter', '$state', 'localStorageService',
 function(AllArticle, $scope, $filter, $state, localStorageService)
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
    $scope.fetchAllData = function()
    {

        $scope.responsed = AllArticle.query(function(results)
        {
            $scope.articles = results.articles;
        });
    };
    $scope.fetchAllData();




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



}])
.filter('startFrom', function(){

    return function(data, start){
        return data.slice(start);
    }

});

app.controller('articleController', ['ArticleWriting', 'DraftsArticleWriting', 'DraftingArticles', 'ArticleWritingServices', '$scope',
'$window', '$http', '$state', '$stateParams', '$interval', '$rootScope', '$timeout', 'localStorageService',
 function(ArticleWriting, DraftsArticleWriting, DraftingArticles, ArticleWritingServices, $scope, $window, $http, $state, $stateParams, $interval, $rootScope, $timeout, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

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

app.controller('updateArticleController', ['UpdatetingArticle', 'UpdatetingArticleServices', '$scope', '$state', '$stateParams', '$window', '$interval', '$timeout', 'localStorageService',
 function(UpdatetingArticle, UpdatetingArticleServices, $scope, $state, $stateParams, $window, $interval, $timeout, localStorageService)
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

            $('#updateSummernote').summernote();

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

    $scope.articleId = '';
    if($stateParams.articleId)
    {
        $scope.articleId = $stateParams.articleId;
    }

    $scope.fetchAllCategories = function()
    {
        $scope.responseData = UpdatetingArticle.query(function(result)
        {

        });
    };
    $scope.fetchAllCategories();
    $scope.article = new UpdatetingArticle();
    $scope.title = '';
    $scope.category = '';

    $scope.fetchCurrentArticle = function()
    {
        var id = $scope.articleId;
        var urlBase = $window.location.origin + $window.location.pathname + '/update-article/' + id;
        UpdatetingArticleServices.fetchArticle(id, urlBase, function(response)
        {
            $scope.setTitle = function()
            {

                $scope.title = response.title;

            };
            $scope.setTitle();
            $scope.setCategory = function()
            {

                $scope.category = response.category;

            };
            $scope.setContent = function()
            {
                $('#updateSummernote').summernote('code', response.content);
            }
            $scope.setContent();
            console.log(response);
        });

    };

    $scope.fetchCurrentArticle();

    $scope.summernoteValue = '';
    var getSummernoteValue = $interval(function()
    {

        $scope.summernoteValue = $('textarea#updateSummernote').val();

    }, 1000);

    $scope.updateArticle = function()
    {
        $scope.$watchGroup(['title', 'category', 'summernoteValue'], function(newValue, oldValue)
        {
            $scope.article.articleId = $scope.articleId;
            if(newValue != oldValue)
            {
                $scope.article.title = $scope.title;
                $scope.article.category = $scope.category;
                $scope.article.content = $scope.summernoteValue;
                UpdatetingArticle.updateArticle({articleId: $scope.article.articleId}, $scope.article, function(response)
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
        $('#savingArticleAndFile').fadeIn();
        $scope.showSavingArticleMsg = true;
        $scope.showSavingFileMsg = false;
        var id = $scope.articleId;
        $timeout(function()
        {
            UpdatetingArticle.getArticle({article: id}, function(response)
            {
                next();
                console.log(response);
            });
        }, 2000);

    };

    $scope.bacToMyArticles = function()
    {

        $state.go('my-articles');

    };

}]);

app.controller('myDraftArticleController', ['DraftArticles', '$scope', '$state', function(DraftArticles, $scope, $state)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    console.log('myDraftArticleController');
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

    $scope.oneNews = function(newsId)
    {

        $state.go('showNews', {

            newsId: newsId

        });

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


app.controller('myArticlesController', ['MyArticles', '$scope', '$state', 'localStorageService', '$timeout',
 function(MyArticles, $scope, $state, localStorageService, $timeout)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.myArticles = new MyArticles();
    $('#aMyArticlesLoader').fadeIn();
    $scope.showMyArticles = false;
    $scope.fetchAllData = function()
    {

        $scope.responsed = MyArticles.query(function(response)
        {
            $('#aMyArticlesLoader').fadeOut();
            $timeout(function()
            {

                $scope.articles = response.articles;
                $scope.articles.forEach(function(data)
                {

                    data.date = new Date(data.dateCreated);

                });
              $scope.showMyArticles = true;
            }, 1000);


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
    $scope.showApprovedDetails = false;
    $scope.showRejectionDetails = false;
    $scope.fetchAdminReview = function(articleId)
    {
        var id = articleId;
        MyArticles.getAdminReview({article: id}, function(response)
        {
            $scope.currentArticleId = response.currentArticleIdCliecked;
            $scope.isApproved = response.isApproved;
            if(response.isApproved === 'OK')
            {
                $scope.approved = response.approvedArticle;
                $scope.showApprovedDetails = true;
            }
            else if(response.isApproved === 'NOT_ACCEPTABLE')
            {
                $scope.rejected = response.articleRejection;
                $scope.showRejectionDetails = true;
            }
        });

    };

    $scope.updateArticle = function(articleId)
    {

        var id = articleId;
        $state.go('updateArticle', {

            articleId: id

        });

    };

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

      $scope.currentState = $state.current.name;
      $scope.$watch('currentState', function(newValue, oldValue)
      {
          if(newValue === 'showArticle')
          {
              $('.sideBar').fadeIn(300);
              $('.headersSection').fadeIn(300);
          }
      });
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
                  $scope.isGoing = response.isGoing;
              }
              else if(response.isGoing === 'Not going')
              {
                  $scope.isGoing = response.isGoing;
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
                  console.log($scope.isGoing);
                  $scope.goingButton = false;
                  $scope.notGoingButton = true;
              }
              else if(response.isGoing === 'Not going')
              {
                  $scope.isGoing = response.isGoing;
                  $scope.goingButton = true;
                  $scope.notGoingButton = false;
              }

          });
      };

      $scope.goingButton = true;
      $scope.notGoingButton = false;

      $scope.going = function()
      {
          $scope.notGoingButton = true;
          $scope.goingButton = false;
      };

      $scope.notGoing = function()
      {
          $scope.notGoingButton = false;
          $scope.goingButton = true;
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


}]);
app.controller('allQuestionsController', ['AllQuestion', '$scope', '$filter', '$state', 'localStorageService',
 function(AllQuestion, $scope, $filter, $state, localStorageService)
{

    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };

    $scope.currentState = $state.current.name;
    $scope.$watch('currentState', function(newValue, oldValue)
    {
        if(newValue === 'questions')
        {
            $('.sideBar').fadeIn(300);
            $('.headersSection').fadeIn(300);
        }
    });
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

app.controller('newQuestionsController', ['$scope', function($scope)
{



}]);

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
        var id = empDetailsId;
        $scope.oneEmployee = People.get({empDetailsId: id}, function(response)
        {
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

    $scope.fetchMyDetails = function()
    {

        $scope.response = MyProfile.query(function(response)
        {

            $scope.userDetails = response.userDetails;
            $scope.empDetails = response.empDetails;
            if(response.hasAnAvatar === 'OK')
            {
                $scope.avatar = response.employeeAvatar;
                $scope.formParams.userAvatar.filetype = $scope.avatar.fileType;
                $scope.formParams.userAvatar.base64 = $scope.avatar.base64;
                // $window.localStorage.setItem('userAvatar', $scope.userAvatar);
                // $window.localStorage.setItem('userAvatar', $scope.userAvatar);

                // console.log($scope.avatar);
            }
            // console.log($scope.userDetails);
            // console.log($scope.empDetails);
            // console.log(response);

        });

    };

    $scope.fetchMyDetails();

    console.log($scope.formParams);


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

app.controller('artcleCategoriesController', ['Category', '$scope', '$state', 'localStorageService',
 function(Category, $scope, $state, localStorageService)
{
    $scope.wideSearch = ''
    $scope.goSearch = function()
    {

        localStorageService.set('search', $scope.wideSearch);
        $state.go('search');

    };
    $scope.currentState = $state.current.name;
    $scope.$watch('currentState', function(newValue, oldValue)
    {
        if(newValue === 'articleCategories')
        {
            $('.sideBar').fadeIn(300);
            $('.headersSection').fadeIn(300);
        }
    });
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

    $scope.listAllCategories = function()
    {
        $scope.categories = Category.query();
        console.log($scope.categories);
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

    $scope.updateCategory = function()
    {

        if($scope.editCategoryForm.$valid)
        {

            $scope.oneCategory.$updateCategory(function()
            {

                alert('Successfully updated');
                angular.element(document).ready(function()
                {

                    $('#pop-wrapper').fadeOut();

                });

                $scope.listAllCategories();
            });

        }

    };

    $scope.clear = function()
    {
        $scope.categoryResources = {};
        $scope.categoriesForm.$setPristine();
        $scope.categoriesForm.$setUntouched();
    };

}]);

app.controller('myCampaignsController', ['$scope', function($scope)
{

}]);


app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', function($stateProvider, $urlRouterProvider, $locationProvider)
{
    $urlRouterProvider.otherwise('/news-feed');
    $stateProvider.
    state('newsFeed', {

        url: '/news-feed',

        views: {
            'newsFeed': {

                templateUrl: 'agentNewsFeed',
                controller: 'newsFeedController'

            }
        }

    }).
    state('search', {

        url: '/search',

        views: {
            'search': {

                templateUrl: 'agentSearch',
                controller: 'searchController'

            }
        }

    }).
    state('dashboard', {

        url: '/dashboard',

        views: {
            'dashboard': {

                templateUrl: 'agentDashboard',
                controller: 'dashboardController'

            }
        }

    }).
    state('showNewsFeedArticle', {

        url: '/news-feed/article/{id}',

        views: {
            'showNewsFeedArticle': {

                templateUrl: 'agentShowNewsFeedArticle',
                controller: 'newsFeedArticleController'

            }
        }

    }).
    state('showNewsFeedQuestion', {

        url: '/news-feed/question/{id}',

        views: {
            'showNewsFeedQuestion': {

                templateUrl: 'agentShowNewsFeedQuestion',
                controller: 'newsFeedQuestionController'

            }
        }

    }).
    state('all-articles', {

        url: '/all-article',

        views: {
            'allArticles': {

                templateUrl: 'agentAllArticles',
                controller: 'allArticleController'

            }
        }

    }).
    state('updateArticle', {

        url: '/update-article/{articleId}',

        views: {
            'updateArticle': {

                templateUrl: 'updateArticle',
                controller: 'updateArticleController'

            }
        }

    }).
    state('new-articles', {

        url: '/new-articles',

        views: {
            'new-articles': {

                templateUrl: 'agent-new-articles',
                controller: 'newArticlesController'

            }
        }

    }).
    state('my-articles', {

        url: '/my-articles',

        views: {
            'my-articles': {

                templateUrl: 'agent-my-articles',
                controller: 'myArticlesController'

            }
        }

    }).
    state('showArticle', {

        url: '/my-articles/{articleId}',

        views: {
            'showArticle': {

                templateUrl: 'agentShowArticle',
                controller: 'myArticleController'

            }
        }

    }).
    state('article', {

        url: '/article/{draftArticleId}',

        views: {
            'article': {

                templateUrl: 'agentArticle',
                controller: 'articleController'

            }
        }

    }).
    state('myDraftArticles', {

        url: '/my-draft-articles',

        views: {
            'myDraftArticles': {

                templateUrl: 'agentMyDraftArticles',
                controller: 'myDraftArticleController'

            }
        }

    }).
    state('allNews', {

        url: '/news',

        views: {
            'allNews': {

                templateUrl: 'agentAllNews',
                controller: 'allNewsController'

            }
        }

    }).
    state('showNews', {

        url: '/news/company-news/{newsId}',

        views: {
            'showNews': {

                templateUrl: 'agentShowNews',
                controller: 'oneNewsController'

            }
        }

    }).
    state('events', {

        url: '/events',

        views: {
            'events': {

                templateUrl: 'agentEvents',
                controller: 'eventsController'

            }
        }

    }).
    state('questions', {

        url: '/all-question',

        views: {
            'all-question': {

                templateUrl: 'agentQuestions',
                controller: 'allQuestionsController'

            }
        }

    }).
    state('new-questions', {

        url: '/questions/new-questions',

        views: {
            'new-questions': {

                templateUrl: 'agent-new-questions',
                controller: 'newQuestionsController'

            }
        }

    }).
    state('my-questions', {

        url: '/questions/my-questions',

        views: {
            'my-questions': {

                templateUrl: 'agent-my-questions',
                controller: 'myQuestionsController'

            }
        }

    }).
    state('people', {

        url: '/people',

        views: {

            'people': {

                templateUrl: 'agentPeople',
                controller: 'peopleController'

            }
        }

    }).
    state('my-profile', {

        url: '/my-profile',

        views: {

            'my-profile': {

                templateUrl: 'agent-my-profile',
                controller: 'myProfileController'

            }
        }

    }).state('articleCategories', {

        url: '/categories',

        views: {


            'articleCategories': {

                templateUrl: 'agentArticleCategories',
                controller: 'artcleCategoriesController'

            }

        }

    });

}]);
