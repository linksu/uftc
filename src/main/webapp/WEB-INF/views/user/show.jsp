<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">K�ytt�j�tiedot</h2>
				</div>
				<div class="chartbox">
					<div class="challenge">
					
					<table>
					<tr>
							<td>
									<p><spring:message code="user.username" text="User name:" /></p>
								</td>

							<td><p>${user.getUsername()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="user.firstName" text="First name:" /></p>
								</td>
							<td><p>${user.getFirstName()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="user.lastName" text="Last name:" /></p>
								</td>
							<td><p>${user.getLastName()}</p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="user.accountType" text="Account type:" /></p>
									</td>
									<td><p><spring:message code="user.${user.getAuthority()}" text="Unknown" /></p></td>
						</tr>
					</table>
					<c:if test="${loggedInUser.getId() == user.getId()}"><a class="nappi" href="/uftc/user/edit?userId=${loggedInUser.getId()}">Muokkaa</a></c:if>
					</div>
					</div>
					</div>
					
			
			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">TAPAHTUMAT</h2>
					</div>
					<img class="headerlogo" src="/uftc/pics/runner.jpg">
					<div class="chartbox">
				<div class="tapahtumat">
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div> 
					</div>
					</div>

				</div>

			</div>
			</div>
			
			
			<!--End of column2-->
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>