$(document).ready(function()
{
    $("input[type = 'search']").focus(function()
    {
        $('.search-page-background').fadeIn();
    });

    $('.search-page-background').on('click', function()
    {

        $('.search-page-background').fadeOut();

    });
    $('#navBar').on('click', function()
    {
        $('.sideBar').toggleClass('hideSideBar');
        $('.header').toggleClass('fullWitdh');
        $('.headerNav').toggleClass('wrapper');
        $('.tempsContainer').toggleClass('tempEvent');
        $('.tempsBox').toggleClass('tempWrapper');
        $('.ui-views').toggleClass('ui-viewsEvent');
        $('.rightSideBar').toggleClass('rightSideBarEvent');
        $('.userListContainer').toggleClass('userListContainerEvent');
        $('.uiContainer').toggleClass('uiContainerEvent');
        $('.search-page-background').toggleClass('search-page-backgroundEvent');
    });

    $('#article').on('click', function()
    {

        $('.articleListAgent').toggleClass('fourRowDisplay');
        // $('.articleList').toggleClass('articleListEvent');

    });

    $('#forums').on('click', function()
    {

        $('.forumnList').toggleClass('threeRowDisplay');

    });

    $('#settings').on('click', function()
    {

        $('.settingsList').toggleClass('threeRowDisplay');

    });



});
