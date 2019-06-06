
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="assets/css/searchbar.css"/>
<div id="navsearch">
    <div class="volver">
            <button type="button" class="btn btn-success btn-circle"  onclick="history.back()"><i class="fas fa-arrow-left"></i>
            </button>
        </div>
    <div class="input_search">
        <form action="Servlet" method="POST">
<div class="input-group md-form form-sm form-2 pl-0">
    
  <input class="form-control my-0 py-1 amber-border" type="text" placeholder="Buscar..." name ="keywords" aria-label="Search" autocomplete="off">
  <div class="input-group-append">
      <button type="submit" class="input-group-text amber lighten-3" id="basic-text1"><i class="fas fa-search text-grey"
        aria-hidden="true"></i></button>
  </div>
</div>
              </form>

        </div>
    <div class="enlace">
        <a href="busqueda_avanzada.jsp"><span>Búsqueda avanzada <i class="fas fa-long-arrow-alt-right"></i></a>
    </div>
</div>