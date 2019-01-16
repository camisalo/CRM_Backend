var Supervisor = /** @class */ (function () {
    function Supervisor(Builder) {
        this.builder = Builder;
    }
    Supervisor.prototype.build = function () {
        this.builder.buildHeader();
        this.builder.buildContent();
        this.builder.buildFooter();
    };
    return Supervisor;
}());
